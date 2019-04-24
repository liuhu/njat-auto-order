package com.acloudchina.hacker.njat.dto.card;

import lombok.Data;

/**
 * @description:
 * @author: LiuHu
 * @create: 2019-04-24 10:27
 * {
 *     "cardId": "0013403058",
 *     "picPath": "http://app.njaoti.com/api/loadpic.jsp?path=/olympic_files/card/jsk.png",
 *     "balance": "260.00",
 *     "cardName": "03健身卡",
 *     "ClosingDate": "2099-01-01",
 *     "purchaseDate": "2019-04-15",
 *     "cardType": "1"
 * }
 **/
@Data
public class MyCardPackageDto {
    private String cardId;
}
