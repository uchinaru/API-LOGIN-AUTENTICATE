const apiUrl = 'http://localhost:8080/api';

document.addEventListener("DOMContentLoaded", () => {
    fetchUsers();

    document.getElementById("userForm").addEventListener("submit", async (e) => {
        e.preventDefault();
        const login = document.getElementById("login").value;
        const password = document.getElementById("password").value;
        const palavraSecreta = document.getElementById("palavraSecreta").value;
        
        await addUser({ login, password, palavraSecreta });
        fetchUsers();
        e.target.reset();
    });
});

async function fetchUsers() {
    const response = await fetch('http://localhost:8080/api/usuarios');
    const users = await response.json();
    const userList = document.getElementById("userList");
    userList.innerHTML = '';

    users.forEach(user => {
        const li = document.createElement("li");
        li.textContent = `Login: ${user.login}`;
        
        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Excluir";
        deleteButton.classList.add("delete-btn");
        deleteButton.addEventListener("click", async () => {
            await deleteUser(user.id);
            fetchUsers();
        });

        li.appendChild(deleteButton);
        userList.appendChild(li);
    });
}

async function addUser(user) {
    const formData = new URLSearchParams();
    formData.append("login", user.login);
    formData.append("password", user.password);
    formData.append("palavraSecreta", user.palavraSecreta);

    try {
        const response = await fetch('http://localhost:8080/api/novo', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData
        });

        if (response.ok) {
            const message = await response.text();
            alert(message);
        } else {
            alert("Erro ao tentar cadastrar o usuário.");
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
        alert("Erro ao tentar cadastrar o usuário.");
    }
}


async function deleteUser(id) {
    await fetch(`${apiUrl}/delete/${id}`, {
        method: 'DELETE'
    });
}