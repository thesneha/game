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
public class UserResponse  {

    private Integer id;
    private String userName;
    private String mobileNo;

    public  static UserResponse from(User user)
    {
        return UserResponse.builder()
                .id(user.getUserId())
                .mobileNo(user.getMobileNo())
                .userName(user.getUserName())
                .build();

    }
}
