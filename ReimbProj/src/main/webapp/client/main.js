function enter() {
    fetch('http://localhost:8080/ReimbProj/main', {
        method: 'GET',
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify(credential)
    })
        .then(resp => {
            if (resp.status === 301) {
                console.log('redirect to login')
                window.location = '/login.html';
            } else {
                //insert long html that is gained from main
            }
        })
}

function approveOne(reimbId) {
    let status = 2;

    let updateInfo = {
        reimbId,
        status
    };

    fetch('http://localhost:8080/ReimbProj/main', {
        method: 'PUT',
        headers: {
            'content-type': 'application/json'
        },
        credentials: 'include', // put credentials: 'include' on every request to use session info //why
        body: JSON.stringify(updateInfo)
    })

}

function denyOne(reimbId) {
    let status = 3;

    let updateInfo = {
        reimbId,
        status
    };

    fetch('http://localhost:8080/ReimbProj/main', {
        method: 'PUT',
        headers: {
            'content-type': 'application/json'
        },
        credentials: 'include', // put credentials: 'include' on every request to use session info //why
        body: JSON.stringify(updateInfo)
    })
}

function createReimb() {

    console.log('creating reimb');

    let amount = document.getElementById('amount').value;
    let description = document.getElementById('description').value;
    let type = document.getElementById('type').value;

    let reimbData = {
        amount,
        description,
        type
    };

    fetch('http://localhost:8080/ReimbProj/main', {
        method: 'POST',
        headers: {
            'content-type': 'application/json'
        },
        credentials: 'include', // put credentials: 'include' on every request to use session info //why
        body: JSON.stringify(reimbData)
    })
        .then(resp => {
            if (resp.status === 201) {
                console.log('submit success')
                document.getElementById('amount').value = '';
                document.getElementById('description').value = "";

                document.getElementById('error-message').innerText = 'Submission Success!';
            }

        })
}