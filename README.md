#쇼핑리스트 앱

<p>kotlin 기반 개발, 지난 프로젝트(recipeDiary)에서 겹치는 코드 참고</p>
<p>주요 기능 -> 쇼핑리스트 저장 / 수정/ 삭제, 각 아이템 체크 및 해제</p>
<p style="background-color:green;">RecyclerView와 Room Database 활용, 코틀린 코드 자체는 조잡한 면이 있어 추후 보완 예정</p>


[해당 앱 구글 플레이스토어 링크]https://play.google.com/store/apps/details?id=com.FallTurtle.shoppingcart

<br>



#사용한 주요 기술

* JetPack
  * Room
    * 쇼핑리스트들을 모바일 기기 내부에 저장하기 위한 데이터베이스 구축에 활용
    * Entity, DAO, Database로 이루어진 기본적인 Room의 구조로 구성
    * 앱에서의 효율적 적용을 위해서 LiveData, ViewModel과 같은 다른 툴들도 사용됨
    * 리스트 Entity 내부에 또 다른 리스트를 추가하기 위해 Room의 TypeConverter 활용 (array -> String)
  * LiveData
    * 앱의 특성 상 사용자가 액티비티를 확인하는 상황에서 데이터 변화가 빈번함 -> LiveData 사용 이유
    * Observer를 통해 DB의 데이터가 변경될 때 바로 그 사항을 액티비티에 반영함
  * ViewModel
    * Room DB를 MVVM 구조로 사용하기 위해서 필요했던 기능
    * 백그라운드에서 동작해야하는 Room의 문제점과 이로 인한 UI 컨트롤러 부담의 문제점을 해결
* ItemTouchHelper
  * 리스트를 이루는 RecyclerView에 드래그 기능을 추가(리스트 삭제 기능을 위해 사용됨)
  * 생성 날짜 별 배치가 좋다고 생각하여 가로 드래그만 추가하고 세로 드래그는 추가하지 않음



<h5>앱 예시 이미지1</h5>
<p>작성한 여러 쇼핑 메모가 리스트로 저장됨</p>

<img src="https://user-images.githubusercontent.com/70795841/123509719-0fa61f00-d6b2-11eb-95b8-725d2f223847.jpg">

<h5>앱 예시 이미지2</h5>
<p>핸드폰 환경을 다크 모드로 지정 시 테마를 자동 변경하여 적용됨</p>

<img src="https://user-images.githubusercontent.com/70795841/123509728-23518580-d6b2-11eb-85fb-1d44347b5e7d.jpg">

<h5>앱 예시 이미지3</h5>
<p>체크리스트 형식으로 다양한 아이템들 기록 및 저장 가능</p>

<img src="https://user-images.githubusercontent.com/70795841/123509699-f309e700-d6b1-11eb-88a7-c1a26b755d03.jpg">
