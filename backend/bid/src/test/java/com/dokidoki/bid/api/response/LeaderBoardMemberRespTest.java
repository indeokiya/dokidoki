package com.dokidoki.bid.api.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("LeaderBoardMemberResp 클래스")
@SpringBootTest
class LeaderBoardMemberRespTest {

    String email = "user01@gmail.com";
    String name = "임아무개";

    LeaderBoardMemberInfo nullNameInfo = LeaderBoardMemberInfo.builder()
            .name(null)
            .build();

    LeaderBoardMemberInfo nullEmailInfo = LeaderBoardMemberInfo.builder()
            .name(name)
            .build();


    LeaderBoardMemberInfo emptyNameInfo = LeaderBoardMemberInfo.builder()
            .name("")
            .build();

    LeaderBoardMemberInfo emptyEmailInfo = LeaderBoardMemberInfo.builder()
            .name(name)
            .build();

    @Test
    public void 생성_null값이_들어갈_때() {
        LeaderBoardMemberResp nullNameResp = LeaderBoardMemberResp.of(nullNameInfo, 1000L);
        LeaderBoardMemberResp nullEmailResp = LeaderBoardMemberResp.of(nullEmailInfo, 1000L);
        System.out.println(nullNameResp);
        System.out.println(nullEmailResp);

    }


    @Test
    public void 생성_빈문자열이_들어갈_때() {
        LeaderBoardMemberResp emptyNameResp = LeaderBoardMemberResp.of(emptyNameInfo, 1000L);
        LeaderBoardMemberResp emptyEmailResp = LeaderBoardMemberResp.of(emptyEmailInfo, 1000L);
        System.out.println(emptyEmailResp);
        System.out.println(emptyNameResp);
    }

}