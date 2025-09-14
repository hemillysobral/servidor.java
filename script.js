function append(value) {
    document.getElementById('display').value += value;
}

function clearDisplay() {
    document.getElementById('display').value = '';
}

function calculate() {
    const expression = document.getElementById('display').value;
    fetch('/calc?expr=' + encodeURIComponent(expression))
        .then(res => res.text())
        .then(result => {
            document.getElementById('display').value = result;
        })
        .catch(() => {
            document.getElementById('display').value = 'Erro';
        });
}
