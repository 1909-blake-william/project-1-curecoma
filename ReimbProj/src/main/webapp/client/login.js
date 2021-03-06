function enter() {
    console.log('page loaded')
    fetch('http://localhost:8080/ReimbProj/login', {
        method: 'GET',
        headers: {
            'content-type': 'application/json'
        }
    })
}

function login() {

    let username = document.getElementById('uname').value;
    let password = document.getElementById('psw').value;
    let credential = {
        username,
        password
    };



    fetch('http://localhost:8080/ReimbProj/login', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        credentials: 'include', // put credentials: 'include' on every request to use session info //why
        body: JSON.stringify(credential)
    })
        .then(resp => {
            if (resp.status === 201) {
                console.log('logged in')
                window.location = '/main.html';
            } else if (resp.status === 251) {
                console.log('logged in')
                window.location = '/main4Managers.html';
            }
            else {
                document.getElementById('error-message').innerText = 'Failed to login';
            }
        })
}