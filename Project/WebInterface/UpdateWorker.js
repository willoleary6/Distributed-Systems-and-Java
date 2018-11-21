self.addEventListener('message', (event) => {
    let returnMessage = event.data;
    setInterval(function () {
        postMessages(returnMessage)
    }, 1000);
})


function postMessages(returnMessage){
    self.postMessage({message: returnMessage});
}