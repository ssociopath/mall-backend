package com.bobooi.mall.data.bo;

import com.bobooi.mall.data.entity.CartGoods;
import com.bobooi.mall.data.entity.PdtInf;
import com.bobooi.mall.data.entity.PdtType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author bobo
 * @date 2021/8/4
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartGoodsBO {
    private CartGoods cartGoods;
    private PdtInf pdtInf;
    private PdtType pdtType;
}
