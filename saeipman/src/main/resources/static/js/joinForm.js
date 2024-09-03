/**
 * joinForm.js
 */

// 생년월일 포맷
let $birth = $("#floatingBirth");
let $birthMsg = $("#birthMsg");
$birth.on("change", (e) => {
  // 생년월일 입력 검증
  if (e.target.value.length != 8 || isNaN(e.target.value)) {
    $birthMsg.text("생년월일: 8자리 숫자를 입력해주세요.");
    $birthMsg.show();
  } else {
    let year = $birth.val().substr(0, 4);
    let month = $birth.val().substr(4, 2);
    let day = $birth.val().substr(6, 2);
    let birthFormat = `${year}/${month}/${day}`;
    $birth.val(birthFormat);
    $birthMsg.hide();
  }
});

// 아이디 중복체크
let $ckIdBtn = $("#ckIdBtn");
let ckIdValid = false;
$ckIdBtn.on("click", () => {
  let $idInput = $("#imaeinId");
  let id = $idInput.val();
  if (!id) {
    alert("아이디 입력");
    $idInput.focus();
    return;
  }
  let dataObj = { id };
  $.ajax({
    url: `${ctxPath}/member/ckId`,
    type: "POST",
    data: dataObj,
    success: function onData(hasId) {
      if (hasId) {
        alert("아이디 중복.");
        ckIdValid = false;
      } else {
        alert("아이디 사용가능.");
        ckIdValid = true;
      }
    },
    error: function onError(error) {
      console.error(error);
    },
  });
});

// form 서브밋 이벤트
/*let $joinfrm = $(document.joinfrm);
$joinfrm.on("submit", (e) => {
  e.preventDefault();
  if (!ckIdValid) {
    alert("아이디 중복확인 해주세요.");
  } else {
  }
});
*/