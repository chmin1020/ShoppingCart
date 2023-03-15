#쇼핑리스트 앱

<p>kotlin 기반 개발, 지난 프로젝트(recipeDiary)에서 겹치는 코드 적용함.</p>
<p>주요 기능 -> 쇼핑리스트 저장 / 수정/ 삭제, 각 아이템 체크 및 해제</p>
<p style="background-color:green;">RecyclerView와 Room Database 활용 학습 목적</p>

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


<h5>앱 예시 이미지1</h5>

<p>작성한 여러 쇼핑 메모가 리스트로 저장됨. 각 쇼핑 메모 세부 사항 수정 및 삭제 가능함.</p>

![main_light](https://user-images.githubusercontent.com/70795841/225316154-3cfe307e-12cc-4ce9-9dd1-f6c0e30d3b5a.jpg)
![main_dark](https://user-images.githubusercontent.com/70795841/225316147-6c9ca7e3-8365-44ed-b124-b05d5a9f7950.jpg)

<p>모바일 기기 설정 상황에 따라 다크모드 역시 적용 가능함.</p>

![list_dark](https://user-images.githubusercontent.com/70795841/225316134-6784d88d-3409-4a72-a977-2dd6022515f6.jpg)
![list_light](https://user-images.githubusercontent.com/70795841/225316143-0bbc93d7-f3e9-4b1c-9660-b989bc508338.jpg)

