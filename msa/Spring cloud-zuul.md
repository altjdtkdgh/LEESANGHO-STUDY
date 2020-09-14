Zuul 이란?
  : 모든 마이크로서비스에 대한 요청을 먼저 받아들이고 라우팅하는 프록시 API Gateway 기능을 수행한다

<img src="https://t1.daumcdn.net/cfile/tistory/99A0213F5C5C2B7F32" style="cursor: pointer;max-width:100%;height:auto" width="860" height="459" filename="msa8.png" filemime="image/jpeg" />


ZUul은 edge 서비스로 다양하게 사용할 수 있습니다.

• 
인증과 보안 : 각 서비스가 분산되어있는 MSA에서 인증을 edge 서비스에서 구현함으로써 모든 서비스에 보안을 구성하지 않고도 보안성있는 어플리케이션 작성을 도모할 수 있습니다.

• 
다이나믹 라우팅 : 호출하는 end-point에 따라 다른 백엔드 서비스의 handler를 호출할 수 있습니다.

• 
모니터링 : 어떤 서비스가 호출되는지 모니터링할 수 있습니다.

• 
Stress 테스트 : 퍼포먼스를 테스트하기 위해 점진적으로 트래픽을 증가시킬 수 있습니다.

• 
Load Shedding : 서로 다른 백엔드 서비스에 대한 요청의 수요량 및 한계를 정할 수 있습니다.

• 
Static Response handling : 특정 요청에 대해서 실제 서비스를 접근하지 않고 zuul에서 바로 응답하게 할 수 있습니다.

• 
Multiregion Resiliency : AWS regions을 넘어 routing 할 수 있도록 해줄 수 있습니다.


그렇다면 zuul은 어떤 구조를 가지고 이런 일을 할 수 있을지 내부 구조에 대해서 알아보도록 하겠습니다.


Zuul의 원리

Zuul의 중심에는 다양한 필터들이 있으며 이러한 필터들을 통해 http 요청에서 응답까지의 라우팅 및 다양한 작업을 수행할 수 있습니다.

기본적으로 Zuul에서 제공하고 있는 기본 Type 필터에는 PRE, ROUTING, POST, ERROR가 있습니다. 이러한 Filter들은 Chaining 방식으로 아래와 같이 거치도록 되어있습니다.


<figure class='imageblock alignCenter' data-filename="zuul_request_lifecycle.png" data-origin-width="960" data-origin-height="720" width="505" height="379"><span data-url='https://blog.kakaocdn.net/dn/cBrBfn/btqBlz6XzgX/C4DRCKBGRtuccpXCSov2r0/img.png' data-lightbox='lightbox' data-alt='zuul request lifecycle, 출처 :&amp;nbsp;https://github.com/Netflix/zuul/wiki'><img src='https://blog.kakaocdn.net/dn/cBrBfn/btqBlz6XzgX/C4DRCKBGRtuccpXCSov2r0/img.png' srcset='https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FcBrBfn%2FbtqBlz6XzgX%2FC4DRCKBGRtuccpXCSov2r0%2Fimg.png' data-filename="zuul_request_lifecycle.png" data-origin-width="960" data-origin-height="720" width="505" height="379"></span><figcaption>zuul request lifecycle, 출처 :&nbsp;https://github.com/Netflix/zuul/wiki</figcaption></figure>


•PRE : origin server에 요청이 전송되기전에 실행되는 routing입니다. request의 인증 / 인가등의 확인 및 부여에 사용할 수 있습니다.
•ROUTING : 실제 origin Server로 라우팅하는 것을 처리하는 filter입니다. Apache http client 또는 Ribbon을 이용하여 http 요청을 작성하고 보냅니다.
•POST : origin server에서 응답을 받은 후 실행되는 filter입니다.
•ERROR : PRE, ROUTING, POST filter 처리중 error가 일어났을 경우 실행되는 filter입니다.


Zuul에는 위 4가지의 기본 적인 필터와 추가적으로 custom를 만들 수 있도록 되어있습니다. 그리고 각 Filter에는 아래와 같이 4가지 주요 키워드를 가지고 있습니다.
•Type : filter가 적용되는 시점(PRE, ROUTING, POST, ERROR).
•Execution Order : fileter가 적용되는 시점(Type) 안에서의 순서
•Criteria : Filter가 실행되는 조건
•Action : Filter가 실행될 때 수행되는 실질적인 로직


이런 특징을 가지고 있는 Zuul은 API Gateway의 형태로 가장 많이 사용하게 됩니다. 해당 시스템을 사용하는 모든 사용자는 End-Point인 Zuul에 요청하고 Zuul에서 실제 서비스로 다이나믹 라우팅을 수행하여 실제 서비스를 접근하는 것입니다. 



Spring Cloud의 적용 과정 : Zuul


Eureka의 설정이 완료 되었으면 Zuul을 적용해 보자. 먼저 API Gateway로 사용될 프로젝트를 생성하고 설정을 진행해야 한다.

1.    Spring Boot 프로젝트를 하나 생성하고 bulid.gradle 파일에 Dependency를 추가한다.
       (Zuul 또한 Eureka Client임을 잊지 말자.)
==============================================================
	implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
	implementation 'org.springframework.cloud:spring-cloud-starter-netflix-zuul'
==============================================================

2.      application.yml 파일에 Zuul설정에 필요한 정보를 추가한다.
        l  라우팅에 관한 설정들을 zuul.routes 밑에 추가한다.
        l  이 Api gateway가 받는 요청의 path가 /board/** 형식이라면 msa-service-board  라는 이름을 갖는 마이크로서비스에 요청을 넘기도록 설정하였다.

===============================================================
server:
  port: 9090
    
spring:
  application:
    name: msa-architecture-zuul

#Config Server      
  cloud:
    config:
      uri: http://localhost:8888 
      name: msa-architecture-config
#Eureka Client   
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:9091/eureka/
      
#Zuul Routing    
zuul:
  ignoredServices: '*'  #routes에 정의되지 않은 모든 요청은 무시함
  routes:
    board:
      path: /board/** #zuul context root
      serviceId: msa-service-board #spring application name
    
    boardMember:
      path: /boardMember/** #zuul context root
      serviceId: msa-service-board-member #spring application name
      

===============================================================



3.      Application.java 파일의 Class에 @EnableZuulProxy와 @EnableDiscoveryClient를 추가한다.
       설정이 완료되면 해당 프로젝트는 Zuul 프록시 역할을 하게된다.
=================================================================
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class MsaArchitectureZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaArchitectureZuulApplication.class, args);
	}
	
	@Bean
	public SimpleFilter simpleFilter() {
		return new SimpleFilter();
	}
}
==================================================================

Custom Filter 추가

Zuul의 Filter를 추가하는 방법은 ZuulFilter을 상속받아서 구현하는 것입니다. 상속을 받게되면 저번 Zuul의 개념편에서 보았던 4가지를 필수적으로 재정의 해주시면 사용할 수 있게 됩니다. 각 내용은 아래에서 보도록 하겠습니다.
public class RouteFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        System.out.println("Inside Route Filter");
        return null;
    }
}
•filterType() : filter의 type으로 "pre", "route", "post", "error", "static"을 용도에 따라 return 하면 됩니다.
•filterOrder() : type안에서 해당 필터가 실행되는 순서입니다.
•shoudFilter() : run method를 실행한다면 true, 실행하지 않아도 된다면 false를 return합니다.
•run() : 실제 filter의 로직을 담당하는 method입니다.

이렇게 만든 Class는 Bean으로 등록하면 filter가 등록되어 사용할 수 있게 됩니다.


예시)
public class SimpleFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
	    RequestContext ctx = RequestContext.getCurrentContext();
	    HttpServletRequest request = ctx.getRequest();

	    System.out.println(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

	    return null;
	}

	@Override
	public String filterType() {
		return "pre";                  //post , routing, error
	}

	@Override
	public int filterOrder() {
		 return 1;
	}


}