function append(val) {
  const d = document.getElementById('display');
  d.value = d.value + val;
}

function clearDisplay() {
  document.getElementById('display').value = '';
}

function calculate() {
  const expr = document.getElementById('display').value;
  fetch('/calc?expr=' + encodeURIComponent(expr))
    .then(res => res.text())
    .then(text => {
      document.getElementById('display').value = text;
    })
    .catch(() => {
      document.getElementById('display').value = 'Erro';
    });
}
