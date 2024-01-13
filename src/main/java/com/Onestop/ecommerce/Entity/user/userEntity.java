package com.Onestop.ecommerce.Entity.user;



import com.Onestop.ecommerce.Entity.Role;
import com.Onestop.ecommerce.Entity.UserMessages.UserMessages;
import com.Onestop.ecommerce.Entity.vendor.Vendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"),indexes = @Index(name = "email_index",columnList = "email",unique = true))
public class userEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(length = 60)
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date DateOfBirth;
    private Long phoneNumber;

    @Column(unique = true )
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<RoleEntity> roles = new ArrayList<>();

    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<UserMessages> userMessages = new ArrayList<>();
    private Long ImageId;
    private boolean isEnabled = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for(RoleEntity role : roles){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;

    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
