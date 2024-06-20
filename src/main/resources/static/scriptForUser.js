

document.addEventListener("DOMContentLoaded", function() {
    fetch("http://localhost:8080/api/authUser")
        .then(response => response.json())
        .then(data => {
            let roles = data.roleSet.map(role => role).join(", ");
            let navbarContent = `
                <span style="color: white">
                    <span>${data.username}</span>
                    <span>with roles:</span>
                    <span>${roles}</span>
                </span>
                <a href="/logout" class="btn btn-outline-light">Logout</a>
            `;
            document.getElementById("navbar").innerHTML = navbarContent;

            let tbodyContent = `
                <tr>
                    <td>${data.id}</td>
                    <td>${data.username}</td>
                    <td>${roles}</td>
                </tr>
            `;
            document.getElementById("user-panel").innerHTML = tbodyContent;
        })
        .catch(error => console.error('Error:', error));
});
