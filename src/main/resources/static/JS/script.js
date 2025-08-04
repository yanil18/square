document.addEventListener('DOMContentLoaded', function () {
  const loginBtn = document.getElementById('login-btn');
  const signupBtn = document.getElementById('signup-btn');
  const loginModal = document.getElementById('login-modal');
  const signupModal = document.getElementById('signup-modal');
  const closeButtons = document.querySelectorAll('.close');

  loginBtn.addEventListener('click', function () {
    loginModal.style.display = 'block';
  });

  signupBtn.addEventListener('click', function () {
    signupModal.style.display = 'block';
  });

  closeButtons.forEach(button => {
    button.addEventListener('click', function () {
      this.closest('.modal').style.display = 'none';
    });
  });

  window.addEventListener('click', function (event) {
    if (event.target.classList.contains('modal')) {
      event.target.style.display = 'none';
    }
  });
});



// console.log("requirement1 at Mobile :: " + globalMobileRequirement);



function test() {
  // alert("dsdsdsds")
  var usr = JSON.stringify({
    value: "test@gmail.com",
  });


  var dd = chkV(usr);

  var settings1 = {
    url: "test?d=" + dd,
    method: "POST",
    timeout: 0,
    headers: {
      "Content-Type": "application/json",
    },
  };
  //console.log(settings1);

  $.ajax(settings1).done(function (j) {
    // j = setV(j);
   // j = JSON.parse(j);
    //alert(j.statusCode);
     console.log(j)
    if (j.statusCode == "1") {
      alert("Mobile no already exists.");
    } else if (j.statusCode == "2") {
      callOTPBasedOnStCode(dd);
    } else if (j.statusCode == "0" && globalMobileRequirement == false) {
      callOTPBasedOnStCode(dd);
    } else {
      alert("User Not Found.");
    }
  });
}

setTimeout(()=>{
test();
},1000)
