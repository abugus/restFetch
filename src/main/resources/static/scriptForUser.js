
fetch("http://localhost:8080/api/authUser").then(
    res => {
        res.json().then(
            data => {
                tbody = ""
                data.roleSet.forEach((r) => {
                        tbody += r + " ";
                    }
                )
                var tbody = `<tr>
                                    <td>${data.id}</td>
                                    <td>${data.username}</td>
                                    <td>
                                    <span>${tbody}</span>
                                    </td>
                                </tr>`;
                document.getElementById("user-panel").innerHTML = tbody;
            }
        )
    }
)
