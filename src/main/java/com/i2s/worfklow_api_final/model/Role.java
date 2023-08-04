    package com.i2s.worfklow_api_final.model;

    import lombok.EqualsAndHashCode;
    import lombok.Getter;
    import lombok.Setter;
    import lombok.ToString;
    import org.springframework.security.core.GrantedAuthority;

    import javax.persistence.*;

    @Entity
    @Getter
    @Setter
    @EqualsAndHashCode
    @ToString
    @Table(name = "roles")
    public class Role implements GrantedAuthority {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @Column(nullable = false)
        private String name;

        public Role() {
        }

        @Override
        public String getAuthority() {
            return name;
        }
    }