package gov.dhs.uscis.odos.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceBeanConfig {
	
	@Bean(name = "org.dozer.Mapper")
	public Mapper getDozerMapper() {
		return new DozerBeanMapper();
	}
	
	

}
