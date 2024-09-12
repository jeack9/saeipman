/**
 * joinForm.js
 */
// 아이디 중복확인
let $ckIdBtn = $("#ckIdBtn");
$ckIdBtn.on("click", () => {
	let $idInput = $("#id");
	let csrf = $("meta[name='_csrf']").attr("value");
	//let id = $idInput.val();
	Swal.fire({
		title: '사용할 아이디를 입력하세요.',
		input: 'text',
		inputAttributes: {
			autocapitalize: 'off' // 자동대소문자
			, autocomplete: "off"
		},
		showCancelButton: true,
		cancelButtonText: "취소",
		confirmButtonText: '중복확인',
		showLoaderOnConfirm: true, // 데이터 결과를 받을때까지, 버튼에다가 로딩바를 표현
		preConfirm: async (id) => { // 확인 버튼 누르면 실행되는 콜백함수
			if (!/^[a-zA-Z][a-zA-Z0-9]{5,17}$/.test(id)) {
				Swal.fire({
					title: `아이디: 조건을 확인하세요.`,
					icon: "warning",
				})
				return;
			}
			try {
				const response = await fetch(`${ctxPath}/all/ckId/${id}`, {
					method: "GET",
					headers: {
						'X-CSRF-TOKEN': csrf,
						'Content-Type': 'application/json'
					}
				});
				console.log(response, "response");
				if (!response.ok) { // 아이디 중복
					throw new Error(response.statusText);
				}
				return await response.json();
			} catch (error) {
				Swal.showValidationMessage(
					`Request failed: ${error}`
				);
			}
		},

		// 실행되는 동안 배경 누를때 모달창 안닫히도록 설정
		// isLoading() 즉, 로딩이 진행되는 동안 false를 리턴하게 해서 ousideClick을 안되게 하고, 로딩 상태가 아니면 ousideClick을 허용한다.
		allowOutsideClick: () => !Swal.isLoading()

	}).then((result) => {
		if (result.value.hasId) {
			Swal.fire({
				title: `중복된 아이디가 있습니다.`,
				icon: "warning",
			})
		} else {
			$idInput.val(result.value.id);
			validateInputs();
		}
	})
});

// 유효성 검사
function validateInputs() {
	let jsValid = true;

	// 이름 검증
	let $nameInput = $("#name");
	let $nameMsg = $("#nameMsg");
	if (!$nameInput.val().trim()) {
		$nameMsg.text("이름: 필수 입력입니다.");
		$nameMsg.addClass("error_text");
		$nameMsg.show();
		jsValid = false;
	} else {
		$nameMsg.hide();
	}

	// 아이디 검증
	let $idInput = $("#id");
	let $idMsg = $("#idMsg");
	let idRegex = /^[a-zA-Z][a-zA-Z0-9]{5,17}$/;
	if (!$idInput.val().trim()) {
		$idMsg.text("아이디: 필수 입력입니다.");
		$idMsg.addClass("error_text");
		$idMsg.show();
		jsValid = false;
	} else {
		$idMsg.hide();
	}
	if (!idRegex.test($idInput.val())) {
		$idMsg.text("아이디: 영문을 포함한 영문 + 숫자 조합 6~18 자리를 사용하세요.");
		$idMsg.addClass("error_text");
		$idMsg.show();
		jsValid = false;
	}

	// 비밀번호 검증
	let $pwInput = $("#pw");
	let $pwMsg = $("#pwMsg");
	let pwRegex = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{6,16}$/;
	if (!$pwInput.val().trim() || !pwRegex.test($pwInput.val())) {
		$pwMsg.show();
		$pwMsg.text("비밀번호: 6~16자 영문자 + 숫자를 조합하세요.");
		$pwMsg.addClass("error_text");
		jsValid = false;
	} else {
		$pwMsg.hide();
	}

	// 이메일 검증
	let $emailInput = $("#email");
	let $emailMsg = $("#emailMsg");
	let emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	if (!$emailInput.val().trim() || !emailRegex.test($emailInput.val())) {
		$emailMsg.show();
		$emailMsg.text("이메일: 올바르지 않은 형식입니다.");
		$emailMsg.addClass("error_text");
		jsValid = false;
	} else {
		$emailMsg.hide();
	}

	// 생년월일 검증 및 포맷
	let $birthInput = $("#birth");
	let $birthMsg = $("#birthMsg");
	let birthRegex = /^\d{8}$/; // 8자리 숫자를 검사하는 정규식
	let inputVal = $birthInput.val().replace(/\D/g, ''); // 숫자만 남기기
	if (!birthRegex.test(inputVal)) {
		$birthMsg.text("생년월일: 8자리 숫자를 입력해주세요.");
		$birthMsg.addClass("error_text");
		$birthMsg.show();
		jsValid = false;
	} else {
		// 형식변환
		let year = inputVal.substr(0, 4);
		let month = inputVal.substr(4, 2);
		let day = inputVal.substr(6, 2);

		// 날짜 형식에 맞게 슬래시 추가
		let birthFormat = `${year}/${month}/${day}`;
		$birthInput.val(birthFormat);

		$birthMsg.hide();
		jsValid = true;
	}

	// 연락처 검증
	let $phoneInput = $("#phone");
	let $phoneMsg = $("#phoneMsg");
	let phoneRegex = /^[0-9]{10,11}$/;
	let phone = $phoneInput.val();
	if (!(phoneRegex.test(phone))) {
		$phoneMsg.text("연락처: 10~11자리의 숫자만 입력가능합니다");
		$phoneMsg.addClass("error_text");
		$phoneMsg.show();
		jsValid = false;
	} else {
		$phoneMsg.hide();
	}

	return jsValid;
}

// 입력값 변경시 유효성 검사
$("#name, #id, #pw, #email, #birth, #phone").on("input", function() {
	validateInputs();
});

// 폼 제출 전 검사
let $joinfrm = $(document.joinfrm);
$joinfrm.on("submit", function(e) {
	if (!validateInputs()) {
		e.preventDefault();
	}
});