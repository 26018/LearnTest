package top.fixyou.entity;


import lombok.*;

/**
 * @author Lsk
 * @description
 * @date 2023/4/26 15:20
 */

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SuperMapResource {

    private String interfaceType;
    private String serviceName;
    private String serviceUrl;

    // 资源名称
    private String name;
    // 资源url
    private String url;
    private String EPSG;

}
