<!DOCTYPE html>
<html>
<head>
    <title>Library Management</title>
</head>
<body>
<h1>Library Management</h1>

<form action="books" method="post">
    <input type="text" name="title" placeholder="Title" required/>
    <input type="text" name="author" placeholder="Author" required/>
    <input type="number" name="year" placeholder="Year" required/>
    <button type="submit">Add Book</button>
</form>

<h2>Books List</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Author</th>
        <th>Year</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%-- Importante: Assegure-se de ter o JSTL (JavaServer Pages Standard Tag Library) disponível para usar as tags --%>
    <c:forEach var="book" items="${books}">
        <tr>
            <td>${book.id}</td>
            <td>${book.title}</td>
            <td>${book.author}</td>
            <td>${book.year}</td>
            <td>
                <form action="delete" method="post">
                    <input type="hidden" name="id" value="${book.id}"/>
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
