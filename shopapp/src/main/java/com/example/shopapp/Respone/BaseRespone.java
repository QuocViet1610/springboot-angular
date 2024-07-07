package com.example.shopapp.Respone;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseRespone {

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
        
}
