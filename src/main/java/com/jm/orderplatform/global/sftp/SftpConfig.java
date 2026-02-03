package com.jm.orderplatform.global.sftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;

@Configuration
public class SftpConfig {

	@Bean
	public DefaultSftpSessionFactory sftpSessionFactory(
		@Value("${sftp.host}") String host,
		@Value("${sftp.port}") int port,
		@Value("${sftp.user}") String user,
		@Value("${sftp.password}") String password
	) {
		DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
		factory.setHost(host);
		factory.setPort(port);
		factory.setUser(user);
		factory.setPassword(password);

		factory.setAllowUnknownKeys(true); // 테스트용, 공개키 검증 옵션 해제

		return factory;
	}

	@Bean
	public SftpRemoteFileTemplate sftpTemplate(
		DefaultSftpSessionFactory sessionFactory,
		@Value("${sftp.dir}") String dir) {

		SftpRemoteFileTemplate template = new SftpRemoteFileTemplate(sessionFactory);

		template.setRemoteDirectoryExpression(
			new LiteralExpression(dir)
		);

		return template;
	}

}
