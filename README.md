# 한줄리스트
<h4>
  Android_Application <br>
  모바일프로그래밍 기말 Term Project
</h4>

***

<div>
  <img src="https://user-images.githubusercontent.com/85846475/205834231-e4a29ace-1601-47a5-bb3f-a2e4cf066bc8.gif" height="350">

  <h5>
    한줄리스트는 체크리스트 기능의 안드로이드 어플리케이션이다. <br>
    쉽고 간단하게 리스트 내용을 수정 및 삭제할 수 있으며, <br>
    직관적인 디자인으로 각 리스트의 내용을 쉽게 파악할 수 있도록 구성하였다.
  </h5>
</div>

***

<h5>
  1. 프로젝트명: 한줄리스트(MemoList) <br>
  2. 개인 프로젝트 <br>
  3. 구현 환경: 안드로이드 스튜디오 Bumblebee(2021.1.1), 코틀린 <br>
  4. 실행 환경: (기기)갤럭시 노트 3 네오, (운영체제)안드로이드 5,0 롤리팝
</h5>

***

### 실행 내용

#### 1. 어플리케이션 실행
<div>
  <img src="https://user-images.githubusercontent.com/85846475/205819788-b4f0ed4b-c649-4546-8d75-a52bf4aeca7c.png" height="150">
  <img src="https://user-images.githubusercontent.com/85846475/205837177-e161c916-4a7d-409f-a335-162de1f528a8.png" height="300">
  <img src="https://user-images.githubusercontent.com/85846475/205836306-8b07b023-4a0d-48f1-85bc-5f74b317dc52.gif" height="300">
  <h6>
    어플리케이션의 아이콘과 아이콘을 터치하여 실행했을 때 나타나는 스플래시 화면이다.<br><br>
    한줄리스트은 두 개의 액티비티로 구성되어있다.<br>
    메인 액티비티에서는 체크 리스트의 타이틀을 나열하여 선택할 수 있으며, 새로운 타이틀을 추가할 수 있다.<br>
    서브 액티비티에서는 선택한 타이틀에 속한 체크리스트를 출력하며, 각 체크리스트의 상태를 체크하거나 해제할 수 있다.
  </h6>
</div>
<br><br>

#### 2. 메인 액티비티
<div>
  <img src="https://user-images.githubusercontent.com/85846475/205838309-44694465-0b32-401e-878d-076fb0a4b974.gif" height="300">
  <h6>
    메인 액티비티에서는 체크 리스트의 타이틀을 선택하거나, 새로운 타이틀을 추가할 수 있다.<br>
    타이틀 추가 버튼을 터치하면 관련 다이얼로그가 생성되며, 입력한 타이틀 데이터를 안드로이드 스튜디오의 Room DB에 추가한다.<br>
    타이틀 리스트는 리스트 프래그먼트로 구성되며, Recycler View에 Room DB의 타이틀 데이터를 출력한다.<br>
    타이틀 리스트에서 타이틀을 선택하면 서브 액티비티로 Intent된다.
  </h6>
</div>
<br>

#### 3. 서브 액티비티
<div>
  <img src="https://user-images.githubusercontent.com/85846475/205839561-dd79dd1a-0e05-4c68-880d-d8db3dede726.gif" height="300">
  <img src="https://user-images.githubusercontent.com/85846475/205840169-f87ccbfa-fe8f-40dd-88a1-4f4685d8647a.gif" height="300">
  <img src="https://user-images.githubusercontent.com/85846475/205840340-633371fb-b423-4da9-81a1-94f0603b7582.gif" height="300">
  <h6>
    서브 액티비티에서는 선택한 타이틀에 속한 체크리스트들을 확인할 수 있다.<br>
    추가 버튼을 터치하여 새로운 체크리스트를 생성할 수 있으며, 체크리스트의 입력란을 터치하여 내용을 수정할 수 있다.<br>
    체크 버튼을 눌러 해당 어플리케이션의 본 기능인 체크 동작을 처리할 수 있다.<br>
    각 리스트를 스와이프하여 삭제할 수 있다.<br>
    타이틀 수정 버튼을 터치하면 다이얼로그가 생성되어 선택한 타이틀의 이름을 수정할 수 있다.<br>
    메인 액티비티와 마찬가지로 리스트 프래그먼트로 구성되며, Room DB의 리스트 데이터를 출력한다.<br>
    메인 액티비티에서 Intent될 때 전달된 타이틀 번호를 포함하는 Room DB의 리스트 데이터만 출력하도록 하였다. 
  </h6>
</div>
<br><br>

***
#### 참고 자료
<h5>
  <ul>
    <li><a href="https://developer.android.com/training/data-storage/room?hl=ko">Android developers_문서: Room</a></li>
    <li>(핵심만 골라 배우는)안드로이드 스튜디오 Arctic Fox & 프로그래밍(닐 스미스, 제이펍)</li>
  </ul>
</h5>
