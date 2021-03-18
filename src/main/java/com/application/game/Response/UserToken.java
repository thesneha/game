package com.application.game.Response;


import com.application.game.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserToken {

    private Integer id;
    private String userName;
    private String mobileNo;
    private Long createdAt;

    public  static UserToken from(User user)
    {
        return UserToken.builder()
                .id(user.getUserId())
                .mobileNo(user.getMobileNo())
                .userName(user.getUserName())
                .createdAt(System.currentTimeMillis()/1000)
                .build();
    }
}
