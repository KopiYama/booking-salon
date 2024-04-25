package com.kopiyama.models;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Service {
    private String serviceId;
    private String serviceName;
    private double price;
}
