let allrows = "";

function enter() {
    let table = document.getElementById('data_rows');
    allrows = "";
    fetch('http://localhost:8080/ReimbProj/main', {
        method: 'GET',
        headers: {
            'content-type': 'application/json'
        }
    })
        .then(resp => {
            if (resp.status === 301) {
                console.log('redirect to login')
                window.location = '/login.html';
            }

            resp.json().then(data => {
                data.forEach(reimbursement => {
                    tableMaker(reimbursement)
                });
            })

        })
}/*GET*/

function enterUser() {
    let table = document.getElementById('data_rows');
    table.innerHTML = "";
    fetch('http://localhost:8080/ReimbProj/main', {
        method: 'GET',
        headers: {
            'content-type': 'application/json'
        }
    })
        .then(resp => {
            if (resp.status === 301) {
                console.log('redirect to login')
                window.location = '/login.html';
            }

            resp.json().then(data => {
                console.log(data)

                data.forEach(reimbursement => {
                    tableMakerUser(reimbursement)
                });
            })

        })
}/*GET*/

function tableMaker(reimbursement) {
    let table = document.getElementById('data_rows');
    if (reimbursement.status === 'Pending') {
        table.innerHTML = table.innerHTML + `<tr class="row${reimbursement.author} status${reimbursement.status} reimb${reimbursement.reimbId}" title="${reimbursement.description}">
        <td id="reimbId">${reimbursement.reimbId}</td>
        <td>${reimbursement.amount}</td>
        <td>${reimbursement.author}</td>
        <td>${reimbursement.type}</td>
        <td>${reimbursement.status}</td>
        <td>${reimbursement.created.substring(0, 19)}</td>
        <td>${reimbursement.resolved.substring(0, 19)}</td>
        <td><button type="button" onclick="approveOne(${reimbursement.reimbId})" class="littleButton">Approve</button></td>
        <td><button type="button" onclick="denyOne(${reimbursement.reimbId})" class="littleButton">Deny</button></td>
        <td id="${reimbursement.reimbId}"><input type="checkbox" value="selected"></td>
        </tr>`;
    } else {
        let table = document.getElementById('data_rows');
        table.innerHTML = table.innerHTML + `<tr class="row${reimbursement.author} status${reimbursement.status} reimb${reimbursement.id}" title="${reimbursement.description}">
        <td id="reimbId">${reimbursement.reimbId}</td>
        <td>${reimbursement.amount}</td>
        <td>${reimbursement.author}</td>
        <td>${reimbursement.type}</td>
        <td>${reimbursement.status}</td>
        <td>${reimbursement.created.substring(0, 19)}</td>
        <td>${reimbursement.resolved.substring(0, 19)}</td>
        </tr>`;
    }
    allrows = table.innerHTML;
}

function tableMakerUser(reimbursement) {
    let table = document.getElementById('data_rows');
    table.innerHTML = table.innerHTML + `<tr title="${reimbursement.description}">
    <td>${reimbursement.reimbId}</td>
    <td>$${reimbursement.amount}</td>
    <td>${reimbursement.author}</td>
    <td>${reimbursement.type}</td>
    <td>${reimbursement.status}</td>
    <td>${reimbursement.created.substring(0, 19)}</td>
    <td>${reimbursement.resolved.substring(0, 19)}</td>
    </tr>`;
}

function filterByUser() {
    document.getElementById('data_rows').innerHTML = allrows;
    let filtered = "";
    let username = document.getElementById('uname').value;
    let table = document.getElementsByClassName("row" + username);

    Array.from(table).forEach(item => {
        filtered = filtered + item.outerHTML;
    });

    if (filtered === "") {
        filtered = allrows;
    }

    document.getElementById('data_rows').innerHTML = filtered;
}

function FilterByStatus() {
    document.getElementById('data_rows').innerHTML = allrows;
    let filtered = "";
    let status = document.getElementById('status').value;
    console.log(status);
    let table = document.getElementsByClassName("status" + status);

    Array.from(table).forEach(item => {
        filtered = filtered + item.outerHTML;
    });

    if (filtered === "" || status === "All") {
        filtered = allrows;
    }

    document.getElementById('data_rows').innerHTML = filtered;
}

function goodbye() {
    fetch('http://localhost:8080/ReimbProj/main', {
        method: 'DELETE',
        headers: {
            'content-type': 'application/json'
        }
    })
}/*DELETE*/

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
}/*PUT*/

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
}/*PUT*/

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
                setTimeout('', 3000);
                document.getElementById('error-message').innerText = '';
            } else {
                document.getElementById('error-message').innerText = 'Submission failed!';
                setTimeout('', 3000);
                document.getElementById('error-message').innerText = '';
            }

        })

    enterUser()
}/*POST*/