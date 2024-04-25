package com.kopiyama.models;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Membership {
    private String memberId;
    private String membershipName;
}
