self.addEventListener('message', (event) => {
    if(event.data = 'start board checker'){
        let returnMessage = event.data;
        setInterval(function () {
            postMessages(returnMessage)
        }, 500);
    }
})


function postMessages(returnMessage){
    self.postMessage({message: returnMessage});
}