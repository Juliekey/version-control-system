/**
 * @author Anna Rysakova
 */
var input = document.getElementById('name')
    , value = input.value;

input.addEventListener('input', onInput);

function onInput(e) {
    var newValue = e.target.value;
    if (newValue.match(/[^a-zA-Z0-9\s\/]/)) {
        input.value = value;
        return;
    }
    value = newValue;
}

inputCD.addEventListener('input', onInputCD);

function onInputCD(e) {
    var newValue = e.target.value;
    if (newValue.match(/[^a-zA-Z0-9\s\\/]/)) {
        inputCD.value = valueCD;
        return;
    }
    valueCD = newValue;
}