console.log("here")

function validate() {
  clearResults();

  var numInvalid = 0;

  for (var row = 0; row < 4; row++) {
    for (var col = 0; col < 4; col++) {
      var control = $('#cube_' + row + '_' + col).get(0)
      if (control.checkValidity() === false || !/[A-Za-z]/.test(control.value)) {
        control.classList.add('invalid');
        numInvalid++;
      } else {
        control.classList.remove('invalid');
      }
      control.classList.add('was-validated');
    }
  }
  if (numInvalid > 0) {
      $('#solve-button').attr('disabled', 'disabled');
  } else {
      $('#solve-button').removeAttr('disabled');
  }

  return numInvalid == 0;
}

function solve() {
  // clear out any previous results
  showSpinner(true);
  clearResults();

  if (validate()) {
    var inputs = $('.needs-validation');

    var data = { board: [] }

    for (var row = 0; row < 4; row++) {
      var colValues = []
      for (var col = 0; col < 4; col++) {
        var control = $('#cube_' + row + '_' + col).get(0);
        colValues.push(control.value.toUpperCase());
      }
      data.board.push(colValues);
    }

    // submit the solve request
    $.ajax({
        type: 'POST',
        url: 'api/solve',
        data: JSON.stringify(data),
        contentType: 'application/json',
        dataType: 'json'
    }).done(function(response) {
      showSpinner(false)
      var wordCount = _.get(response, 'length', 0);
      $('#word-count').html(wordCount);

      if (wordCount > 0) {
        var list = $('#valid-words')
        list.append('<ul class="list-group">');

        response.sort().forEach(function(word) {
          list.append('<li class="list-group-item">' + word + '</li>');
        })

        list.append('</ul>');
      }
    })
  }
}

function getRandomLetter() {
  var random = Math.floor(Math.random() * 26) + 1
  return 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'[random - 1]
}

function fillBoardRandomly() {
  clearResults();
  for (var row = 0; row < 4; row++) {
    for (var col = 0; col < 4; col++) {
      var control = $('#cube_' + row + '_' + col).get(0);
      control.value = getRandomLetter();
    }
  }
  $('#solve-button').removeAttr('disabled');
}

function clearResults() {
  $('#valid-words').html('')
  $('#word-count').html('');
}

function showSpinner(show) {
  if (show) {
    $('#loading').get(0).classList.remove('hidden')
  } else {
    $('#loading').get(0).classList.add('hidden')
  }
}