Zuul 이란?
  : 모든 마이크로서비스에 대한 요청을 먼저 받아들이고 라우팅하는 프록시 API Gateway 기능을 수행한다

Zuul의 원리

Zuul의 중심에는 다양한 필터들이 있으며 이러한 필터들을 통해 http 요청에서 응답까지의 라우팅 및 다양한 작업을 수행할 수 있습니다.

기본적으로 Zuul에서 제공하고 있는 기본 Type 필터에는 PRE, ROUTING, POST, ERROR가 있습니다. 이러한 Filter들은 Chaining 방식으로 아래와 같이 거치도록 되어있습니다.


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

1. Spring Boot 프로젝트를 하나 생성하고 bulid.gradle 파일에 Dependency를 추가한다. (Zuul 또한 Eureka Client임을 잊지 말자.)

==============================================================
implementation 'org.springframework.cloud:spring-cloud-starter-netflix-eureka-client'
implementation 'org.springframework.cloud:spring-cloud-starter-netflix-zuul'
==============================================================

2. application.yml 파일에 Zuul설정에 필요한 정보를 추가한다.  라우팅에 관한 설정들을 zuul.routes 밑에 추가한다. 이 Api gateway가 받는 요청의 path가 /board/** 형식이라면 msa-service-board  라는 이름을 갖는 마이크로서비스에 요청을 넘기도록 설정하였다.

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



3. Application.java 파일의 Class에 @EnableZuulProxy와 @EnableDiscoveryClient를 추가한다. 설정이 완료되면 해당 프로젝트는 Zuul 프록시 역할을 하게된다.


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

==============================================================================
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
===========================================================================

•filterType() : filter의 type으로 "pre", "route", "post", "error", "static"을 용도에 따라 return 하면 됩니다.

•filterOrder() : type안에서 해당 필터가 실행되는 순서입니다.

•shoudFilter() : run method를 실행한다면 true, 실행하지 않아도 된다면 false를 return합니다.

•run() : 실제 filter의 로직을 담당하는 method입니다.

이렇게 만든 Class는 Bean으로 등록하면 filter가 등록되어 사용할 수 있게 됩니다.



===============================================================================
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
===================================================================================