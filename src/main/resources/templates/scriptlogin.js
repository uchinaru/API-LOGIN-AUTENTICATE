
document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("userForm").addEventListener("submit", async (e) => {
        e.preventDefault();
        const login = document.getElementById("login").value;
        const password = document.getElementById("password").value;
        const palavraSecreta = document.getElementById("palavraSecreta").value;
        
        await logar({ login, password, palavraSecreta });
        e.target.reset();
    });
});

async function logar(user) {
    const formData = new URLSearchParams();
    formData.append("login", user.login);
    formData.append("password", user.password);
    formData.append("palavraSecreta", user.palavraSecreta);

    try {
        const response = await fetch('http://localhost:8080/api/logar', {
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
            alert("Erro ao logar");
        }
    } catch (error) {
        console.error("Erro na requisição:", error);
        alert("Erro ao logar.");
    }
}
