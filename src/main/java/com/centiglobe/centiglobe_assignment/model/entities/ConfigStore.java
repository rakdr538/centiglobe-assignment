package com.centiglobe.centiglobe_assignment.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "config_store")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ConfigStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Pattern(regexp = "^[a-z0-9]+([-\\.][a-z0-9]+)*$", message = "Member name does not match required pattern")
    @Column(name = "member_name")
    private String memberName;

    @NotNull
    @Min(value = 0, message = "Connections should be greater than or equal to 0")
    @Max(value = 32, message = "Connections should be less than or equal to 32")
    @Column(name = "max_connections")
    private Integer maxConnections;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ConfigStatus status;

}