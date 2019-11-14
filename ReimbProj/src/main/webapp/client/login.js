function login() {

    let username = document.getElementById('uname').value;
    let password = document.getElementById('psw').value;
    let credential = {
        username,
        password
    };

    console.log(credential);

    fetch('http://localhost:8080/ReimbProj/login', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        credentials: 'include', // put credentials: 'include' on every request to use session info //why
        body: JSON.stringify(credential)
    })
    .then(resp => {
        if(resp.status === 201) {
            console.log('go to ')
            window.location = '/main.html';
        } else {
            document.getElementById('error-message').innerText = 'Failed to login';
        }
    })
}