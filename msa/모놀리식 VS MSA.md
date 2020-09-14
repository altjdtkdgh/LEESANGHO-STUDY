<figure class='imageblock alignCenter' data-origin-width="0" data-origin-height="0" width="618" height="543" data-filename="blob"><span data-url='https://blog.kakaocdn.net/dn/49wTn/btqASzgr8wR/jLuZ4oQHoMyv4CnkeTxBC0/img.png' data-lightbox='lightbox' data-alt=''><img src='https://blog.kakaocdn.net/dn/49wTn/btqASzgr8wR/jLuZ4oQHoMyv4CnkeTxBC0/img.png' srcset='https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2F49wTn%2FbtqASzgr8wR%2FjLuZ4oQHoMyv4CnkeTxBC0%2Fimg.png' data-origin-width="0" data-origin-height="0" width="618" height="543" data-filename="blob"></span></figure>

모놀리식 아키텍처 (Monolithic Architecture)

장점

1. 어떤 기능(서비스)이든지 개발되어있는 환경이 같아서 복잡하지않다.

2. 쉽게 고가용성 서버 환경을 만들 수 있다. ( 같은 어플리케이션으로 하나더 만들면 됨)

3. End-to-End 테스트가 용이하다. (MSA의 경우 테스트에 필요한 서비스들을 모두 동작시켜야함)

 

단점

1. 한 프로젝트의 덩치가 너무 커져서 어플리케이션 구동시간이 늘어나고 빌드,배포 시간도 길어진다.

2. 조그마한 수정사항이 있어도 전체를 다시 빌드하고 배포를 해야한다.

3. 많은 양의 코드가 몰려있어 개발자가 모두를 이해 할 수 없고 유지보수도 힘들다.

4. 일부분의 오류가 전체에 영향을 미친다.

5. 기능별로 알맞는 기술, 언어, 프레임워크를 선택하기가 까다롭다.

 

마이크로 서비스 (MicroService)

장점

1. 기능별로 마이크로서비스를 개발하고, 작업 할당을 서비스 단위로 하면 개발자가 해당 부분을 온전히 이해할 수 있다.

2. 새로 추가되거나 수정사항이 있는 마이크로서비스만 빠르게 빌드, 배포가 가능하다.

3. 해당 기능에 맞는 기술, 언어 등을 선택하여 사용할 수 있다.

4. 일부분의 오류가 있으면 해당 기능에만 오류가 발생하고 그 부분만 빠르게 고쳐서 정상화가 가능하다.

 

단점

1. 무엇보다 관리가 힘들다. 작은 여러 서비스들이 분산되어있기 때문에 모니터링이 힘들다.

2. 서로를 호출하여 전체 서비스가 이루어지기 때문에 무조건 다른 서비스를 호출하는 코드가 추가되는데

이부분이 모놀리식 아키텍쳐의 개발보다 조금 까다롭다.

3. 통신관련 오류가 잦을 수 있다. 마이크로 서비스들 끼리 계속 서로 통신을 하다보니 모놀리식 아키텍쳐에 비해 통신관련 오류가 잦았다.

4. 테스트가 불편하다. 예로 End-to-End 테스트를 위해 UI, Gateway 등등 여러개의 마이크로 서비스를 구동시켜야 했었다. 

 

 

개인적인 의견

이전 회사에서 마이크로서비스 환경으로 개발을 하였고,

현재 회사에서는 모놀리식 아키텍쳐로 개발되어있는 프로젝트에 투입되어있는데

개인적으로는 마이크로서비스가 개발에 조금더 용이한 것 같다.

 

가장 큰 이유는

매 주마다 새로운 기획안과 수정요청이 생기는데 조그마한 변경사항에도 전체를 다시 빌드하고 배포를 해야하니

부담이되고 한 기능을 수정하였는데 잘못된 부분이 있어 전체가 오류가 생기는 경우도 있었다. 따라서 모든 기능을 다시 테스트를 하고 배포가 되는데 이것 또한 부담이었다.

 

처음부터 마이크로 서비스로 개발하는 것은 모놀리식 아키텍쳐의 개발방식보다 불편한 것은 확실하다.

하지만 빠르게 변화해야하는 IT 서비스 시장에서 발 맞춰 개발하려면 마이크로 서비스를 선택하는것이 좋을것이다.

 
