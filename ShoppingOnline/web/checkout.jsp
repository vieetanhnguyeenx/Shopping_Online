<%-- 
    Document   : detail
    Created on : Jan 2, 2023, 5:55:04 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>LongWatchShop</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
    </head>

    <body>
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container px-4 px-lg-5">
                <a class="navbar-brand" href="home">LongWatch</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0 ms-lg-4">
                        <li class="nav-item"><a class="nav-link active" aria-current="page" href="home">Home</a></li>
                        <li class="nav-item"><a class="nav-link" href="#!">About</a></li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Shop</a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#!">All Products</a></li>
                                <li>
                                    <hr class="dropdown-divider" />
                                </li>
                                <li><a class="dropdown-item" href="#!">Popular Items</a></li>
                                <li><a class="dropdown-item" href="#!">New Arrivals</a></li>
                            </ul>
                        </li>
                    </ul>
                    <form class="d-flex mx-auto">
                        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                        <button class="btn btn-outline-success" type="submit">Search</button>
                    </form>
                    <a class="d-flex my-2" href="carts" style="text-decoration: none">
                        <button class="btn btn-outline-dark" type="submit">
                            <i class="bi-cart-fill me-1"></i>
                            Cart
                            <span class="badge bg-dark text-white ms-1 rounded-pill">${sessionScope.carts.size()}</span>
                        </button>
                    </a>
                    <button class="btn btn-outline-success me-2 ms-lg-2" type="button">Login</button>
                </div>
            </div>
        </nav>

        <div class="py-3">

            <div class="container px-4 px-lg-5 mt-5 vh-100">
                <h1 class="">CheckOut</h1>
                <div class="row">
                    <div class="col-md-8 border border-1 rounded p-1">
                        <c:if test="${sessionScope.carts.isEmpty()}" var="condition">
                            <h2 class="bg-light text-dark">List cart is empty</h2>
                            <p class="fw-light">Lorem ipsum dolor sit amet consectetur adipisicing elit. Repellendus ullam exercitationem eius delectus numquam sint ut facere blanditiis molestiae corrupti. Et voluptatem at hic laudantium. Quaerat adipisci ad veritatis ex!</p>
                        </c:if>
                        <c:if test="${!condition}">
                            <h3 class="bg-light text-dark">List Products</h3>

                            <table class="table">
                                <thead>
                                    <tr>
                                        <th scope="col">Id</th>
                                        <th scope="col">Image</th>
                                        <th scope="col">Name</th>
                                        <th scope="col">Price</th>
                                        <th scope="col">Quantity</th>
                                        <th scope="col">Total</th>
                                        <th scope="col">Action</th>
                                    </tr>
                                </thead>
                                <tbody class="my-auto">

                                    <c:forEach items="${sessionScope.carts}" var="c">
                                    <form action="update-cart-quantity" method="GET">
                                        <tr>
                                        <input type="hidden" name="productId" value="${c.value.product.id}" />
                                        <th scope="row">${c.value.product.id}</th>
                                        <td><img src="${c.value.product.imageUrl}" class="rounded-2" alt="alt" width="100"/></td>
                                        <td>${c.value.product.name}</td>
                                        <td>${c.value.product.price}</td>
                                        <td><input onchange="this.form.submit()" name="quantity" class="btn btn-outline-dark" type="number" value="${c.value.quantity}"></td>
                                        <td>${c.value.product.price * c.value.quantity} </td>
                                        <td><a href="delete-cart?productId=${c.value.product.id}" class="btn btn-outline-danger"><i class="bi bi-trash"></i> Delete</a></td>
                                        </tr>
                                    </form>
                                </c:forEach>
                            <!-- <input type="number" value="${c.value.quantity}"> -->
                                </tbody>
                            </table>
                        </c:if>
                        <div class="d-flex justify-content-end">
                            <h3 class="my-auto">Total Amount: $${requestScope.totalMoney}</h3>
                        </div>
                    </div>
                    <div class="col-md-4 border border-1 rounded p-1">
                        <h3 class="bg-light text-dark">Information of customer</h3>
                        <form>
                            <div class="mb-3">
                                <label for="exampleInputEmail1" class="form-label">Email address</label>
                                <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
                                <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
                            </div>
                            <div class="mb-3">
                                <label for="exampleInputPassword1" class="form-label">Password</label>
                                <input type="password" class="form-control" id="exampleInputPassword1">
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="exampleCheck1">
                                <label class="form-check-label" for="exampleCheck1">Check me out</label>
                            </div>
                            <button type="submit" class="btn btn-primary w-100">Submit</button>
                        </form>
                    </div>
                </div>

            </div>
        </div>

        <!-- Footer-->
        <footer class="py-5 bg-dark">
            <div class="container">
                <p class="m-0 text-center text-white">Copyright &copy; LongWatch</p>
            </div>
        </footer>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
    </body>

</html>