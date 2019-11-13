function login(event) {
    event.preventDefault();

    const username = document.getElementById('uname').value;
    const password = document.getElementById('psw').value;
    const credential = {
        username,
        password
    };

    fetch('http://localhost:8080/ReimbProj/webPage/login.html', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        credentials: 'include', // put credentials: 'include' on every request to use session info
        body: JSON.stringify(credential)
    })
    .then(resp => {
        if(resp.status === 201) {
            // redirect
            console.log('go to ')
            window.location = '/pokemon/view-pokemon/view-pokemon.html';
        } else {
            document.getElementById('error-message').innerText = 'Failed to login';
        }
    })


}