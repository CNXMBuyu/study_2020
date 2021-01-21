package com.accloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guoyu.huang
 * @since 2021-01-19
 */
public class File {

    public static void main(String[] args) {

        String text = "2021-01-20 22:31:57 [main] INFO  Hive:274 - 开始执行增量文件的增加文件，数量为：81----------------->\n" +
                "2021-01-20 22:31:57 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AFFICHE_ABNOPER.hive' into table affiche_abnoper\n" +
                "2021-01-20 22:31:58 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:31:58 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AFFICHE_BASE.hive' into table affiche_base\n" +
                "2021-01-20 22:31:58 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:31:58 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AFFICHE_ILLEGALITY.hive' into table affiche_illegality\n" +
                "2021-01-20 22:31:58 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:31:58 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AFFICHE_SPOTCHECK.hive' into table affiche_spotcheck\n" +
                "2021-01-20 22:31:59 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:31:59 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_ALTERSTOCKINFO.hive' into table an_alterstockinfo\n" +
                "2021-01-20 22:31:59 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:31:59 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_BASEINFO.hive' into table an_baseinfo\n" +
                "2021-01-20 22:31:59 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:31:59 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_FORGUARANTEEINFO.hive' into table an_forguaranteeinfo\n" +
                "2021-01-20 22:31:59 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:31:59 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_FORINVESTMENT.hive' into table an_forinvestment\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_PB_BASEINFO.hive' into table an_pb_baseinfo\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_PB_LICENCEINFO.hive' into table an_pb_licenceinfo\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_PB_UPDATEINFO.hive' into table an_pb_updateinfo\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_PB_WEBSITEINFO.hive' into table an_pb_websiteinfo\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_SFC_BASEINFO.hive' into table an_sfc_baseinfo\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_SFC_BRANCHINFO.hive' into table an_sfc_branchinfo\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_SFC_LICENCEINFO.hive' into table an_sfc_licenceinfo\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_SFC_SOCSECINFO.hive' into table an_sfc_socsecinfo\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:00 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_SFC_SUP_BASEINFO.hive' into table an_sfc_sup_baseinfo\n" +
                "2021-01-20 22:32:01 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:01 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_SFC_UPDATEINFO.hive' into table an_sfc_updateinfo\n" +
                "2021-01-20 22:32:01 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:01 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_SFC_WEBSITEINFO.hive' into table an_sfc_websiteinfo\n" +
                "2021-01-20 22:32:01 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:01 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_SOCSECINFO.hive' into table an_socsecinfo\n" +
                "2021-01-20 22:32:01 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:01 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_SUBCAPITAL.hive' into table an_subcapital\n" +
                "2021-01-20 22:32:01 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:01 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_SUP_BASEINFO.hive' into table an_sup_baseinfo\n" +
                "2021-01-20 22:32:02 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:02 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_UPDATEINFO.hive' into table an_updateinfo\n" +
                "2021-01-20 22:32:02 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:02 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AN_WEBSITEINFO.hive' into table an_websiteinfo\n" +
                "2021-01-20 22:32:02 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:02 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AO_OPANOMALY.hive' into table ao_opanomaly\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AO_OPA_DETAIL.hive' into table ao_opa_detail\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AO_PB_OPANOMALY.hive' into table ao_pb_opanomaly\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AO_SFCBR_OPADETAIL.hive' into table ao_sfcbr_opadetail\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AO_SFCBR_OPANOMALY.hive' into table ao_sfcbr_opanomaly\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AO_SFC_OPADETAIL.hive' into table ao_sfc_opadetail\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_AO_SFC_OPANOMALY.hive' into table ao_sfc_opanomaly\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_CASE_PUB_ALTER.hive' into table case_pub_alter\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_CASE_PUB_BASEINFO.hive' into table case_pub_baseinfo\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:03 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_CASE_PUB_PARTYINFO.hive' into table case_pub_partyinfo\n" +
                "2021-01-20 22:32:04 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:04 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_ALTER_RECODER.hive' into table e_alter_recoder\n" +
                "2021-01-20 22:32:04 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:04 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_BASEINFO.hive' into table e_baseinfo\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_BRCHINFO.hive' into table e_brchinfo\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_CANCEL.hive' into table e_cancel\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_FACT_CONTRIBUTION.hive' into table e_fact_contribution\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_GT_ALTER_RECODER.hive' into table e_gt_alter_recoder\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_GT_CANCEL.hive' into table e_gt_cancel\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_GT_REVOKE.hive' into table e_gt_revoke\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_IM_CASALT.hive' into table e_im_casalt\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_IM_CASE.hive' into table e_im_case\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:05 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_IM_INVACTDETAIL.hive' into table e_im_invactdetail\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_IM_INVESTMENT.hive' into table e_im_investment\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_IM_INVPRODETAIL.hive' into table e_im_invprodetail\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_IM_INVSRALT.hive' into table e_im_invsralt\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_IM_INVUPDATE.hive' into table e_im_invupdate\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_IM_IPPLDG.hive' into table e_im_ippldg\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_IM_IPPLDGALT.hive' into table e_im_ippldgalt\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_IM_PERMIT.hive' into table e_im_permit\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_IM_PRMTALT.hive' into table e_im_prmtalt\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_INV_INVESTMENT.hive' into table e_inv_investment\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_INV_PAID_IN.hive' into table e_inv_paid_in\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:06 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_INV_PERSON.hive' into table e_inv_person\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_LIQ_MBR.hive' into table e_liq_mbr\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_LI_ILLDISDETAIL.hive' into table e_li_illdisdetail\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_LI_ILLDISHONESTY.hive' into table e_li_illdishonesty\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_LP_HSTNAME.hive' into table e_lp_hstname\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_OT_CASE.hive' into table e_ot_case\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_OT_PERMIT.hive' into table e_ot_permit\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_OT_PRMTALT.hive' into table e_ot_prmtalt\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:07 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_PB_BASEINFO.hive' into table e_pb_baseinfo\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_PB_OPERATOR.hive' into table e_pb_operator\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_PRI_PERSON.hive' into table e_pri_person\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_PUB_SPOTCHECK.hive' into table e_pub_spotcheck\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_REVOKE.hive' into table e_revoke\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_SC_NOTICE.hive' into table e_sc_notice\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_SC_OBJECTION.hive' into table e_sc_objection\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:08 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_SF_ALTER.hive' into table e_sf_alter\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_SF_INFO.hive' into table e_sf_info\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_SF_PARTYINFO.hive' into table e_sf_partyinfo\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_SP_ALTER.hive' into table e_sp_alter\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_E_SP_PLEDGE.hive' into table e_sp_pledge\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_MORT_ALTITEM_INFO.hive' into table mort_altitem_info\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_MORT_GUARANTEE_INFO.hive' into table mort_guarantee_info\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_MORT_PERSON_INFO.hive' into table mort_person_info\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_MORT_PRINCIPAL_CLAIM.hive' into table mort_principal_claim\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:342 - execute success\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:340 - execute：load data local inpath '/home/xm_eis/bigdata/data/20201214_MORT_REG_INFO.hive' into table mort_reg_info\n" +
                "2021-01-20 22:32:09 [main] INFO  Hive:342 - execute success";

        StringBuilder delete = new StringBuilder("rm ");
        while (text.contains("'")) {
            int start = text.indexOf("'");
            text = text.replaceFirst("'", "");
            int end = text.indexOf("'");
            text = text.replaceFirst("'", "");
            String table = text.substring(start, end);


            delete.append(" " + table);
        }

        System.out.println(delete.toString());
    }
}
