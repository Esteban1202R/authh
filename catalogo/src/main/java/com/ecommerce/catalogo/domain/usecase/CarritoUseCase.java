package com.ecommerce.catalogo.domain.usecase;

import com.ecommerce.catalogo.domain.exception.NoEncontradoException;
import com.ecommerce.catalogo.domain.exception.ValidacionNegocioException;
import com.ecommerce.catalogo.domain.model.Carrito;
import com.ecommerce.catalogo.domain.model.ItemCarrito;
import com.ecommerce.catalogo.domain.model.Producto;
import com.ecommerce.catalogo.domain.model.Usuario;
import com.ecommerce.catalogo.domain.model.gateway.CarritoGateway;
import com.ecommerce.catalogo.domain.model.gateway.ProductoGateway;
import com.ecommerce.catalogo.domain.model.gateway.UsuarioGateway;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CarritoUseCase {

    private static final String ACTIVO = "ACTIVO";
    private static final String COMPRADO = "COMPRADO";

    private final CarritoGateway carritoGateway;
    private final ProductoGateway productoGateway;
    private final UsuarioGateway usuarioGateway;

    // Asigna un carrito a un usuario existente. Solo puede tener uno activo.
    public Carrito crearCarrito(Long idUsuario) {
        Usuario usuario = usuarioGateway.buscarPorIdUsuario(idUsuario);

        if (usuario.getId() == null) {
            throw new NoEncontradoException("El usuario no existe");
        }

        Carrito carritoActivo = carritoGateway.buscarCarritoActivo(idUsuario);

        if (carritoActivo.getId() != null) {
            throw new ValidacionNegocioException("El usuario ya tiene un carrito activo");
        }

        Carrito carrito = new Carrito();
        carrito.setIdUsuario(idUsuario);
        carrito.setEstado(ACTIVO);
        carrito.setTotal(0.0);
        carrito.setItems(new ArrayList<>());

        return carritoGateway.guardarCarrito(carrito);
    }

    public Carrito consultarCarrito(Long idUsuario) {
        Carrito carrito = carritoGateway.buscarCarritoActivo(idUsuario);

        if (carrito.getId() == null) {
            throw new NoEncontradoException("El usuario no tiene un carrito activo");
        }

        return carrito;
    }

    // Agregar al carrito no descuenta inventario ni cobra: solo reserva la intencion
    public Carrito agregarProducto(Long idUsuario, Long idProducto, Integer cantidad) {
        Carrito carrito = consultarCarrito(idUsuario);
        Producto producto = buscarProductoExistente(idProducto);

        validarCantidad(cantidad);

        ItemCarrito itemExistente = buscarItem(carrito, idProducto);

        // Agregar el mismo producto dos veces suma la cantidad, no duplica el item
        int cantidadFinal = cantidad;
        if (itemExistente != null) {
            cantidadFinal = itemExistente.getCantidad() + cantidad;
        }

        validarStockDisponible(producto, cantidadFinal);

        if (itemExistente != null) {
            itemExistente.setCantidad(cantidadFinal);
            itemExistente.setSubtotal(producto.getPrecio() * cantidadFinal);
        } else {
            ItemCarrito item = new ItemCarrito();
            item.setIdProducto(producto.getId());
            item.setNombreProducto(producto.getNombre());
            item.setCantidad(cantidad);
            item.setPrecioUnitario(producto.getPrecio());
            item.setSubtotal(producto.getPrecio() * cantidad);
            carrito.getItems().add(item);
        }

        calcularTotal(carrito);
        return carritoGateway.guardarCarrito(carrito);
    }

    public Carrito actualizarCantidad(Long idUsuario, Long idProducto, Integer cantidad) {
        Carrito carrito = consultarCarrito(idUsuario);
        Producto producto = buscarProductoExistente(idProducto);

        validarCantidad(cantidad);
        validarStockDisponible(producto, cantidad);

        ItemCarrito item = buscarItem(carrito, idProducto);

        if (item == null) {
            throw new NoEncontradoException("El producto no esta en el carrito");
        }

        item.setCantidad(cantidad);
        item.setPrecioUnitario(producto.getPrecio());
        item.setSubtotal(producto.getPrecio() * cantidad);

        calcularTotal(carrito);
        return carritoGateway.guardarCarrito(carrito);
    }

    public Carrito quitarProducto(Long idUsuario, Long idProducto) {
        Carrito carrito = consultarCarrito(idUsuario);

        ItemCarrito item = buscarItem(carrito, idProducto);

        if (item == null) {
            throw new NoEncontradoException("El producto no esta en el carrito");
        }

        carrito.getItems().remove(item);

        calcularTotal(carrito);
        return carritoGateway.guardarCarrito(carrito);
    }

    // La compra: valida usuario, stock y saldo antes de tocar nada
    public Carrito comprar(Long idUsuario) {
        Carrito carrito = consultarCarrito(idUsuario);

        if (carrito.getItems().isEmpty()) {
            throw new ValidacionNegocioException("El carrito esta vacio");
        }

        Usuario usuario = usuarioGateway.buscarPorIdUsuario(idUsuario);

        if (usuario.getId() == null) {
            throw new NoEncontradoException("El usuario no existe");
        }

        // Primero se comprueba TODO el inventario, para no dejar la compra a medias
        List<Producto> productos = new ArrayList<>();

        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = buscarProductoExistente(item.getIdProducto());
            validarStockDisponible(producto, item.getCantidad());
            productos.add(producto);
        }

        calcularTotal(carrito);

        if (usuario.getSaldo() == null || usuario.getSaldo() < carrito.getTotal()) {
            throw new ValidacionNegocioException("Saldo insuficiente");
        }

        // El descuento de saldo lo hace auth, que es el dueno del dinero
        usuarioGateway.descontarSaldo(idUsuario, carrito.getTotal());

        // Ya cobrado, se descuenta el inventario
        for (int i = 0; i < carrito.getItems().size(); i++) {
            ItemCarrito item = carrito.getItems().get(i);
            Producto producto = productos.get(i);

            producto.setStock(producto.getStock() - item.getCantidad());
            productoGateway.actualizarProducto(producto);
        }

        carrito.setEstado(COMPRADO);
        return carritoGateway.guardarCarrito(carrito);
    }

    private Producto buscarProductoExistente(Long idProducto) {
        Producto producto = productoGateway.buscarPorIdProducto(idProducto);

        if (producto.getId() == null) {
            throw new NoEncontradoException("El producto no existe");
        }

        return producto;
    }

    private ItemCarrito buscarItem(Carrito carrito, Long idProducto) {
        for (ItemCarrito item : carrito.getItems()) {
            if (item.getIdProducto().equals(idProducto)) {
                return item;
            }
        }
        return null;
    }

    private void validarCantidad(Integer cantidad) {
        if (cantidad == null || cantidad <= 0) {
            throw new ValidacionNegocioException("La cantidad debe ser mayor que cero");
        }
    }

    private void validarStockDisponible(Producto producto, Integer cantidad) {
        if (producto.getStock() < cantidad) {
            throw new ValidacionNegocioException("Stock insuficiente para el producto " + producto.getNombre());
        }
    }

    // El total siempre se calcula en el servidor, nunca llega desde el cliente
    private void calcularTotal(Carrito carrito) {
        double total = 0.0;

        for (ItemCarrito item : carrito.getItems()) {
            total = total + item.getSubtotal();
        }

        carrito.setTotal(total);
    }
}
