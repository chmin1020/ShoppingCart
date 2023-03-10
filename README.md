#쇼핑리스트 앱

<p>kotlin 기반 개발, 지난 프로젝트(recipeDiary)에서 겹치는 코드 적용함.</p>
<p>주요 기능 -> 쇼핑리스트 저장 / 수정/ 삭제, 각 아이템 체크 및 해제</p>
<p style="background-color:green;">RecyclerView와 Room Database 중점 활용</p>

https://play.google.com/store/apps/details?id=com.FallTurtle.shoppingcart

<br>



#사용한 주요 기술

* JetPack
  * Room
    * 쇼핑리스트들을 모바일 기기 내부에 저장하기 위한 데이터베이스 구축에 활용
    * Entity, DAO, Database로 이루어진 기본적인 Room의 구조로 구성
    * 리스트 Entity 내부에 또 다른 리스트를 추가하기 위해 Room의 TypeConverter 활용 (array -> String)
  * LiveData
    * 앱의 특성 상 사용자가 액티비티를 확인하는 상황에서 데이터 변화가 빈번함 -> LiveData 사용 이유
    * Observer를 통해 DB의 데이터가 변경될 때 바로 그 사항을 액티비티에 반영함
  * ViewModel
    * Room DB를 MVVM 구조로 사용하기 위해서 AAC 뷰모델을 활용
    * 백그라운드에서 동작해야하는 Room DB 작업을 분리하여 배치함으로써 UI 컨트롤러(Activity) 부담의 문제점을 해결
* Hilt
  * 2023 추가 - hilt 학습 및 다른 앱 추가 적용을 위한 테스트 및 코드 리팩토링의 과정의 일환
  * room, repository, viewModel 부분에 hilt를 통한 종속성 주입 활용



--앱 사용 이미지에 변경 사항 생김 (추후 readme에 적용)--

<h5>앱 예시 이미지1</h5>
<p>작성한 여러 쇼핑 메모가 리스트로 저장됨</p>

<img src="https://user-images.githubusercontent.com/70795841/123509719-0fa61f00-d6b2-11eb-95b8-725d2f223847.jpg">

<h5>앱 예시 이미지2</h5>
<p>핸드폰 환경을 다크 모드로 지정 시 테마를 자동 변경하여 적용됨</p>

<img src="https://user-images.githubusercontent.com/70795841/123509728-23518580-d6b2-11eb-85fb-1d44347b5e7d.jpg">

<h5>앱 예시 이미지3</h5>
<p>체크리스트 형식으로 다양한 아이템들 기록 및 저장 가능</p>

<img src="https://user-images.githubusercontent.com/70795841/123509699-f309e700-d6b1-11eb-88a7-c1a26b755d03.jpg">
