export function ApiHelper(url, data = {}, method = 'GET') {
    return fetch(url, {  // Return promise
        method: method,
        withCredentials: false,
        headers:{
            'x-api-key':'intelli-search-csh',
            'Content-Type': 'application/json',
        },
       body:JSON.stringify(data)

    })
        .then(res => res.json())
        .then((result) => {
            return result;
        }, (error) => {
            return error;
        })
}