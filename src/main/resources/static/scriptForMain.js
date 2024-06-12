
//для верхней шутки где показывается авторизованный юзер
var tbody
fetch("http://localhost:8080/api/authUser").then(
    res => {
        res.json().then(
            data => {
                const roles = data.roleSet;
                tbody = " <span style=\"color: white\">";
                tbody += "<span>" + data.username + " </span>"
                tbody += "<span>with roles: </span>";
                tbody += "<span>"
                roles.forEach((r) => {
                        tbody += r + " ";
                    }
                )
                tbody += "</span>";
                tbody += "</span>";
                tbody += "<a href=\"/logout\">Logout</a>"
                document.getElementById("navbar-toggle-button").innerHTML = tbody;
            }
        )
    }
)
//для таблицы юзеров
const url = "http://localhost:8080/api/people"
let roles = []
var $j = jQuery.noConflict();
let table = '';
const renderPosts = (data) => {
    table = ''
    data.forEach(user => {
        let roles = '';
        table += `<tr id="${user.id}" class="lchlhf123">
            <td> ${user.id}</td>
            <td> ${user.username}</td>
            <td>`;
        user.roleSet.forEach(role => {
            roles += role + '\n'
            table += `<span>${role} </span>`;
        })
        table += `<td>
                        <!-- Button edit -->
                        <button type="button" class="btn btn-primary" data-toggle="modal"
                         data-target="${'#Modal_Edit_' + user.id}">
                            Edit
                        </button>
                        <!-- Modal edit -->
                        <div class="modal fade" id="${'Modal_Edit_' + user.id}" tabindex="-1"
                             role="dialog"
                             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLongTitle">Edit
                                            user</h5>
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                          </button>
                                    </div>
                                    <div class="modal-body tab-pane fade show active card">
                                        <form  id="formEdit_${user.id}">
                                            <br>
                                            <div class="form-group">
                                                <label class="form-label"
                                                       for="edit-id"><strong>Id</strong></label>
                                                <input class="form-control" type="text" name="id"
                                                       value="${user.id}" id="edit-id${user.id}" disabled />
                                            </div>
                                            <div class="form-group">
                                                <label class="form-label"
                                                       for="edit-name"><strong>Name</strong></label>
                                                <input class="form-inline" type="text" name="name"
                                                       value="${user.username}" id="edit-name${user.id}"/>
                                            </div>
                                            <br>
                                            <div class="form-group">
                                                <label class="form-label" for="edit-password"><strong>Password</strong></label>
                                                <input class="form-inline" type="text" name="pass"
                                                       value="12345" id="edit-password${user.id}"/>
                                                <br>
                                            </div>
                                            <div class="form-label  form-group">
                                                <label class="form-label"
                                                       for="edit-role"><strong>Role</strong></label>
                                                <select class="custom-select" id="edit-role${user.id}"
                                                        name="role[]" multiple size="2">
                                                    <option value="ADMIN">ADMIN</option>
                                                    <option value="USER">USER</option>
                                                </select>
                                            </div>
                                            <br>
                                            <br>
                                            <br>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" id="close-edit"
                                                        data-dismiss="modal">Close
                                                </button>
                                                <input type="submit" id="buttonEdit" class="btn btn-primary"
                                                       value="Edit">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </td>`
        table += `<!-- Button delete -->
                                    <td>
                                        <button  type="button" class="btn btn-danger" data-toggle="modal"
                                                data-target="${'#Modal_Delete_' + user.id}">
                                            Delete
                                        </button>
                                        <!-- Modal delete -->
                                        <div class="modal fade" id="${'Modal_Delete_' + user.id}" tabindex="-1"
                                             role="dialog"
                                             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">Delete
                                                            user</h5>
                                                        <button type="button" class="close" data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body tab-pane fade show active card">
                                                        <form id="formDelete_${user.id}">
                                                            <br>
                                                            <div class="form-group">
                                                                <label class="form-label"
                                                                       for="delete-id"><strong>Id</strong></label>
                                                                <input class="form-control" type="text" name="id"
                                                                       value="${user.id}" id="delete-id${user.id}" readonly/>
                                                            </div>
                                                            <div class="form-group">
                                                                <label class="form-label"
                                                                       for="delete-name"><strong>Name</strong></label>
                                                                <input class="form-control" type="text" name="name"
                                                                       value="${user.username}" id="delete-name${user.id}"
                                                                       readonly/>
                                                            </div>
                                                            <br>
                                                            <div class="form-group">
                                                                <label class="form-label" for="delete-password"><strong>Password</strong></label>
                                                                <input class="form-control" type="text" name="pass"
                                                                       value="******" id="delete-password${user.id}" readonly/>
                                                                <br>
                                                            </div>
                                                            <div class="form-label  form-group">
                                                                <label class="form-label"
                                                                       for="delete-role"><strong>Role</strong></label>
                                                                <select class="custom-select" id="delete-role${user.id}"
                                                                        name="role[]" multiple size="2" disabled>
                                                                    <option readonly>${roles}</option>
                                                               </select>
                                                            </div>
                                                            <br>
                                                            <br>
                                                            <br>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" id="close-delete"
                                                                        data-dismiss="modal">Close
                                                                </button>
                                                                <input type="submit" id="buttonDelete" class="btn btn-danger"
                                                                       value="Delete">
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>`
        document.getElementById("user-table").innerHTML = table
    })
}
fetch(url).then(res => res.json())
    .then(data => renderPosts(data))
let userId;
document.getElementById("user-table").addEventListener('click', (e) => {
    e.preventDefault();
    let delButtonPressed = e.target.id === `buttonDelete`;
    let editButtonPressed = e.target.id === `buttonEdit`;
    if (e.target.parentElement.parentElement.className === "lchlhf123") {
        userId = e.target.parentElement.parentElement.id;
    }
    if (delButtonPressed) {
        fetch(`${url}/${userId}`, {
            method: 'DELETE',
        }).then(res => res.json())
            .then(data => renderPosts(data))
        document.getElementById('close-delete').click()
        $('.modal-backdrop').remove();
        $("<div></div>").replaceWith(document.getElementById(userId))
    }
    if (editButtonPressed) {
        const nameEdit = document.getElementById(`edit-name${userId}`);
        const passwordEdit = document.getElementById(`edit-password${userId}`);
        const rolesUserEdit = document.getElementById(`edit-role${userId}`);


        let roles = [];
        for (const option of rolesUserEdit.selectedOptions) {
            roles.push(option.value)
        }

        const d = {
            id: userId,
            username: nameEdit.value,
            pass: passwordEdit.value,
            roleSet: roles
        };

        fetch(url, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(d)
        })
        $('.modal-backdrop').remove();
        document.getElementById('close-edit').click()
        const div = $(`edit-name${userId}`);
        table = `<tr id="${userId}" class=lchlhf123>
            <td> ${userId}</td>
            <td> ${nameEdit.value}</td>
            <td>`;

        table += `<span>${roles}</span>`;

        table += `<td>
                        <!-- Button edit -->
                        <button type="button" class="btn btn-primary" data-toggle="modal"
                         data-target="${'#Modal_Edit_' + userId}">
                            Edit
                        </button>
                        <!-- Modal edit -->
                        <div class="modal fade" id="${'Modal_Edit_' + userId}" tabindex="-1"
                             role="dialog"
                             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLongTitle">Edit
                                            user</h5>
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                          </button>
                                    </div>
                                    <div class="modal-body tab-pane fade show active card">
                                        <form  id="formEdit_${userId}">
                                            <br>
                                            <div class="form-group">
                                                <label class="form-label"
                                                       for="edit-id"><strong>Id</strong></label>
                                                <input class="form-control" type="text" name="id"
                                                       value="${userId}" id="edit-id${userId}" disabled />
                                            </div>
                                            <div class="form-group">
                                                <label class="form-label"
                                                       for="edit-name"><strong>Name</strong></label>
                                                <input class="form-inline" type="text" name="name"
                                                       value="${nameEdit.value}" id="edit-name${userId}"/>
                                            </div>
                                            <br>
                                            <div class="form-group">
                                                <label class="form-label" for="edit-password"><strong>Password</strong></label>
                                                <input class="form-inline" type="text" name="pass"
                                                       value="12345" id="edit-password${userId}"/>
                                                <br>
                                            </div>
                                            <div class="form-label  form-group">
                                                <label class="form-label"
                                                       for="edit-role"><strong>Role</strong></label>
                                                <select class="custom-select" id="edit-role${userId}"
                                                        name="role[]" multiple size="2">
                                                    <option value="ADMIN">ADMIN</option>
                                                    <option value="USER">USER</option>
                                                </select>
                                            </div>
                                            <br>
                                            <br>
                                            <br>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" id="close-edit"
                                                        data-dismiss="modal">Close
                                                </button>
                                                <input type="submit" id="buttonEdit" class="btn btn-primary"
                                                       value="Edit">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </td>`
        table += `<!-- Button delete -->
                                    <td>
                                        <button  type="button" class="btn btn-danger" data-toggle="modal"
                                                data-target="${'#Modal_Delete_' + userId}">
                                            Delete
                                        </button>
                                        <!-- Modal delete -->
                                        <div class="modal fade" id="${'Modal_Delete_' + userId}" tabindex="-1"
                                             role="dialog"
                                             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">Delete
                                                            user</h5>
                                                        <button type="button" class="close" data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body tab-pane fade show active card">
                                                        <form id="formDelete_${userId}">
                                                            <br>
                                                            <div class="form-group">
                                                                <label class="form-label"
                                                                       for="delete-id"><strong>Id</strong></label>
                                                                <input class="form-control" type="text" name="id"
                                                                       value="${userId}" id="delete-id${userId}" readonly/>
                                                            </div>
                                                            <div class="form-group">
                                                                <label class="form-label"
                                                                       for="delete-name"><strong>Name</strong></label>
                                                                <input class="form-control" type="text" name="name"
                                                                       value="${nameEdit.value}" id="delete-name${userId}"
                                                                       readonly/>
                                                            </div>
                                                            <br>
                                                            <div class="form-group">
                                                                <label class="form-label" for="delete-password"><strong>Password</strong></label>
                                                                <input class="form-control" type="text" name="pass"
                                                                       value="******" id="delete-password${userId}" readonly/>
                                                                <br>
                                                            </div>
                                                            <div class="form-label  form-group">
                                                                <label class="form-label"
                                                                       for="delete-role"><strong>Role</strong></label>
                                                                <select class="custom-select" id="delete-role${userId}"
                                                                        name="role[]" multiple size="2" disabled>
                                                                    <option readonly>${roles}</option>
                                                               </select>
                                                            </div>
                                                            <br>
                                                            <br>
                                                            <br>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" id="close-delete"
                                                                        data-dismiss="modal">Close
                                                                </button>
                                                                <input type="submit" id="buttonDelete" class="btn btn-danger"
                                                                       value="Delete">
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>`
        $(document.getElementById(userId)).replaceWith(table)
    }
})


//добавление юзера
const renderPosts2 = (data) => {
    var tbody = ` <h5 class="card-body" style="font-size: x-large">Add new user</h5>
                        <div class="tab-pane fade show active card" role="tabpanel">
                            <form id="addUser">
                                <br>
                                <div class="form-group">
                                    <label class="form-label" for="name"><strong>Name</strong></label>
                                    <input class="form-inline" type="text" th:field="*{name}" id="name"/>
                                </div>
                                <br>
                                <div class="form-group">
                                    <label class="form-label" for="password"><strong>Password</strong></label>
                                    <input class="form-inline" type="text" th:field="*{pass}" id="password"/>
                                    <br>
                                </div>
                                <div class="form-label  form-group">
                                    <label class="form-label" for="role"><strong>Role</strong></label>
                                    <select class="custom-select" id="role" name="role[]" multiple size="2">
                                        <option value="ADMIN">ADMIN</option>
                                        <option value="USER">USER</option>
                                    </select>
                                    <div class="invalid-feedback">Example invalid custom select feedback</div>
                                </div>
                                <br>
                                <input id="button-add" class="btn btn-success center" type="submit" value="Add new user">
                                <br>
                                <br>
                            </form>
                        </div>`;
    document.getElementById("profile").innerHTML = tbody;

}


document.getElementById("profile").addEventListener('click', (e) => {
    e.preventDefault();
    let addButtonPressed = e.target.id === `button-add`;
    if (addButtonPressed) {
        const nameEdit = document.getElementById(`name`);
        const passwordEdit = document.getElementById(`password`);
        const rolesUserEdit = document.getElementById(`role`);
        let roles = [];
        var addUserId;
        for (const option of rolesUserEdit.selectedOptions) {
            roles.push(option.value)
        }

        const d = {
            username: nameEdit.value,
            pass: passwordEdit.value,
            roleSet: roles
        };
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(d)
        }).then(res => res.json())
            .then(data => {
                addUserId = data;
                $j.ajax(`${url}`, {
                    dataType: "json",
                    success: function (user) {
                        const div = $("#user-table");
                        table = `<tr id="${addUserId}" class=lchlhf123>
            <td> ${addUserId}</td>
            <td> ${nameEdit.value}</td>
            <td>`;

                        table += `<span>${roles}</span>`;

                        table += `<td>
                        <!-- Button edit -->
                        <button type="button" class="btn btn-primary" data-toggle="modal"
                         data-target="${'#Modal_Edit_' + addUserId}">
                            Edit
                        </button>
                        <!-- Modal edit -->
                        <div class="modal fade" id="${'Modal_Edit_' + addUserId}" tabindex="-1"
                             role="dialog"
                             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLongTitle">Edit
                                            user</h5>
                                        <button type="button" class="close" data-dismiss="modal"
                                                aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                          </button>
                                    </div>
                                    <div class="modal-body tab-pane fade show active card">
                                        <form  id="formEdit_${addUserId}">
                                            <br>
                                            <div class="form-group">
                                                <label class="form-label"
                                                       for="edit-id"><strong>Id</strong></label>
                                                <input class="form-control" type="text" name="id"
                                                       value="${addUserId}" id="edit-id${addUserId}" disabled />
                                            </div>
                                            <div class="form-group">
                                                <label class="form-label"
                                                       for="edit-name"><strong>Name</strong></label>
                                                <input class="form-inline" type="text" name="name"
                                                       value="${nameEdit.value}" id="edit-name${addUserId}"/>
                                            </div>
                                            <br>
                                            <div class="form-group">
                                                <label class="form-label" for="edit-password"><strong>Password</strong></label>
                                                <input class="form-inline" type="text" name="pass"
                                                       value="12345" id="edit-password${addUserId}"/>
                                                <br>
                                            </div>
                                            <div class="form-label  form-group">
                                                <label class="form-label"
                                                       for="edit-role"><strong>Role</strong></label>
                                                <select class="custom-select" id="edit-role${addUserId}"
                                                        name="role[]" multiple size="2">
                                                    <option value="ADMIN">ADMIN</option>
                                                    <option value="USER">USER</option>
                                                </select>
                                            </div>
                                            <br>
                                            <br>
                                            <br>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" id="close-edit"
                                                        data-dismiss="modal">Close
                                                </button>
                                                <input type="submit" id="buttonEdit" class="btn btn-primary"
                                                       value="Edit">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </td>`
                        table += `<!-- Button delete -->
                                    <td>
                                        <button  type="button" class="btn btn-danger" data-toggle="modal"
                                                data-target="${'#Modal_Delete_' + addUserId}">
                                            Delete
                                        </button>
                                        <!-- Modal delete -->
                                        <div class="modal fade" id="${'Modal_Delete_' + addUserId}" tabindex="-1"
                                             role="dialog"
                                             aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">Delete
                                                            user</h5>
                                                        <button type="button" class="close" data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body tab-pane fade show active card">
                                                        <form id="formDelete_${addUserId}">
                                                            <br>
                                                            <div class="form-group">
                                                                <label class="form-label"
                                                                       for="delete-id"><strong>Id</strong></label>
                                                                <input class="form-control" type="text" name="id"
                                                                       value="${addUserId}" id="delete-id${addUserId}" readonly/>
                                                            </div>
                                                            <div class="form-group">
                                                                <label class="form-label"
                                                                       for="delete-name"><strong>Name</strong></label>
                                                                <input class="form-control" type="text" name="name"
                                                                       value="${nameEdit.value}" id="delete-name${addUserId}"
                                                                       readonly/>
                                                            </div>
                                                            <br>
                                                            <div class="form-group">
                                                                <label class="form-label" for="delete-password"><strong>Password</strong></label>
                                                                <input class="form-control" type="text" name="pass"
                                                                       value="******" id="delete-password${addUserId}" readonly/>
                                                                <br>
                                                            </div>
                                                            <div class="form-label  form-group">
                                                                <label class="form-label"
                                                                       for="delete-role"><strong>Role</strong></label>
                                                                <select class="custom-select" id="delete-role${addUserId}"
                                                                        name="role[]" multiple size="2" disabled>
                                                                    <option readonly>${roles}</option>
                                                               </select>
                                                            </div>
                                                            <br>
                                                            <br>
                                                            <br>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" id="close-delete"
                                                                        data-dismiss="modal">Close
                                                                </button>
                                                                <input type="submit" id="buttonDelete" class="btn btn-danger"
                                                                       value="Delete">
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>`
                        $(table).appendTo(div);

                    }
                })
            })
    }
})
fetch(url).then(res => res.json())
    .then(data => renderPosts2(data))
//панель юзера
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
