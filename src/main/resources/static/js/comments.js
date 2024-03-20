const backendLocation = 'http://localhost:8080'

let routeId = document.getElementById("routeId").getAttribute("value")
let commentSection = document.getElementById("commentCtnr")

fetch(`${backendLocation}/api/${routeId}/comments`)
    .then((response) => response.json())
    .then((body) => {
        for (comment of body) {
            addCommentAsHtml(comment)
        }
    })

function addCommentAsHtml(comment) {
    let commentHtml = '<div class="comments">\n'
    commentHtml += '<div hidden>' + comment.id + '</div>'
    commentHtml += '<h4>' + comment.authorFullName + '</h4>\n'
    commentHtml += '<p>' + comment.textContent + '</p>\n'
    commentHtml += '<span>' + comment.created + '</span>\n'
    commentHtml += '</div>\n'

    commentSection.innerHTML += commentHtml
}

const csrfHeaderName = document.getElementById("csrf").getAttribute("name")
const csrfHeaderValue = document.getElementById("csrf").getAttribute("value")

let commentForm = document.getElementById("commentForm")
commentForm.addEventListener("submit", (event) => {
    event.preventDefault()

    let text = document.getElementById("message").value

    fetch(`${backendLocation}/api/${routeId}/comments`, {
        method: 'POST',
        headers: {
            'Content-type': 'application/json',
            'Accept': 'application/json',
            [csrfHeaderName]: csrfHeaderValue
        },
        body: JSON.stringify({
            textContent: text
        })
    }).then((res) => {
        let location = res.headers.get("Location")
        fetch(`${location}`)
            .then(res => res.json())
            .then(body => addCommentAsHtml(body))
    })
})