package com.acloudchina.hacker.njat.controller;

import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderQueryDto;
import com.acloudchina.hacker.njat.dto.venue.order.VenueOrderResponseBodyDto;
import com.acloudchina.hacker.njat.service.transport.VenueTransportService;
import com.acloudchina.hacker.njat.utils.EncryptionUtils;
import com.acloudchina.hacker.njat.utils.GetKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-15 22:18
 **/
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private VenueTransportService venueTransportService;


    @GetMapping("/venueOrders")
    public VenueOrderResponseBodyDto getVenueOrder(String date) {
        VenueOrderQueryDto queryDto = new VenueOrderQueryDto();
        queryDto.setVenueId("60497855257431");
        queryDto.setDate(null != date ? date : "2019-04-30");
        queryDto.setUserId("34693021860297337");
        return venueTransportService.getVenueOrder(queryDto);
    }

    /**
     * 解码测试
     * @param args
     */
    public static void main(String[] args) {
        // otherPayCheck
        String str1 = "697a0d2c0b7bc5da2b864b085a92f79fa42faf98d5710b72b23c6b99f97db0fa3d4f19e980890b326d2aa2946c087b5651a572e580872d8ec717265512c10ecd9758b6a145139dee7f7afc308bb55a11737c5c9f33583adc988876b9e927ad64e843e22bf33001a359f54293f15d96c4a3927b2c3d3a93763ef786abf3a549c343562f4105c2e501733f60ad28d42d0cf78a1f04171d6d4899557df0f118fbe6a24dabf6daa8f17d2a75e09a3ddd872a737c5c9f33583adc988876b9e927ad64d9f5c154dca9e9b0a62ecb4798d5a383c9afac2470bef9737eaea7f2b03f194f4b681612217ddee00631adcabee3c686465e8e64f30e0fb189cf33e37c8979bd9a21257af150002237e575a4bc91ace7a81d28a4d955b74f8b3fcd5f2f1d1a9a6a523361185f443b6a5f7b190f3e1a21f27fc24c3e3b6da2c7be31ea85253b7bb1cc8ded4811a163d1aa9823a1ae18e56f3cd6528e114db9e5e79d84c08419d8a4c1f6c92cb9192346e5137b86f855d5a66d96176180ff6cd347161ca82a969078a584acbf560f8437b99929da6ba5b1c578c289c5fd0de3ff39d307548635b69eb5f353d7dd455b2330f38da9c097ff7cd4441e5115754cb14a52989216c24dd81466b0825887e3d93f030efec435dbcb2c7a7e3c6b40f9ccbbc81bbcf80b8d87610b516ac096f5a6d32337eab104c7b362f836e7285a7899a069ace2f0c774e039717b4367820fcc1b44a29a73a5829e97669ade6fa54d3907e16d6934e84f0e6db54f946d0927c9d4e738310d7002c6852167361121f3d7273ea0464c21cedff4994f4f2243087b5d8bd284b71c6f170ed9317e810c8581df30895a820f9a176798a76be17c271cddcfa9cee203836a5f62f6bc1469e53aed2013e80cb168720bf3cec5b4ba08092e3e2b307ff50d7df5343a47afec9a9986141f9085879ab6a29261be17419d537198c6e4b87ca34033a67d9a87440db94b9d2161fa9e0435317663edcb22e727f090de900f7780c7eaaeb6664569ce41afe5602399b2e677ed2503e3c28f826b4bd960c7bec9530f22476e208804fd69a5738fc5357a0642301b8c940eae1224a113a59fbaa8b1c6b44468fb897ec042333b4a22b4cf72737c5c9f33583adc988876b9e927ad64d9f5c154dca9e9b0a62ecb4798d5a383c9afac2470bef9737eaea7f2b03f194f4b681612217ddee00631adcabee3c686bdbe5ea72455b6667f554d61efdaf51a25113e06bafc93b78e615de91e53323e711b9b6149ad6aa2dd000a00ee17649bb2743ad4701ca297d0d4d615d526e9f0f3cb7bf87e5028f4466c97ebe2f55ac0cba04118114b02140772b7a22a16cb85c691c1d9b7a3f054c9eb5037191861c69d204492a62429079986b4497801054cc6aa67b97cb571f78b353daca447e88e0d4eb84285bc78e1ce7c447d0bac654eef369d14b6f279a4ab8493b453d3d29594c2ee74f832df9225ce6381e873f3cee1d7408d84fb705806ed0380942b81ef8e567f3999e97ec6f917a65b386169b3b1c5784e965d5815bc88643f2a3ef5628cb46c9b50bdb0f55c6f9cfdaa7b598d622b0561596cc7ac723a7225ba1860373ad207df88543056cb7313f47053a0693fde006e32b725b605084b7b7956fdb546341d35cc44f727cd94e887d4cf68272b93bcb21b291fcf91dfd56989c1803cf04875f7d684b44dbff1b3e98388851bb557aa76e6ffca7a86910eec68dea8744f4c8fd9107f365422cc8efe7e130704";
        System.out.println(EncryptionUtils.decodeFromAes(str1,  GetKeyUtils.getKey("8")));

        // otherPayCheck
        String str2 = "dabd89b2ecf25c06ddae0315509b5a2f";
        System.out.println(EncryptionUtils.decodeFromAes(str2,  GetKeyUtils.getKey("8")));
    }
}
