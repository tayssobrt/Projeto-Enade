package com.ads.enade.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRankingDTO {

    private String username;
    private Integer score;

    public UserRankingDTO(String username, Integer score) {
        this.username = username;
        this.score = score;
    }

}
