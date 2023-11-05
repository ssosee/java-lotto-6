package lotto.view;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import lotto.util.StringUtils;

public class LottoMachineDisplay {
    private static final String START_BRACKET = "[";
    private static final String END_BRACKET = "]";
    private static final String START_SMALL_BRACKET = "(";
    private static final String END_SMALL_BRACKET = ")";
    private static final String HYPHEN = "-";
    private static final String BLANK = " ";
    private static final String NEW_LINE = "\n";
    private static final String COMMA = ",";
    private static final String PROMPT_PURCHASE_LOTTO_AMOUNT_MESSAGE = "구매금액을 입력해주세요.";
    private static final String PROMPT_WINNING_LOTTO_NUMBER_MESSAGE = "당첨 번호를 입력해 주세요.";
    private static final String PROMPT_BONUS_LOTTO_NUMBER_MESSAGE = "보너스 번호를 입력해 주세요.";
    private static final String PURCHASED_LOTTO_MESSAGE = "개를 구매했습니다.";
    private static final String MATCHED_LOTTO_MESSAGE = "개 일치";
    private static final String LOTTO_COUNT_UNIT_MESSAGE = "개";
    private static final String MATCHED_BONUS_LOTTO_MESSAGE = "보너스 볼 일치";
    private static final String LOTTO_STATISTICS_MESSAGE = "당첨 통계";
    private static final String TOTAL_LOTTO_YIELD_RATE = "총 수익률은 %.1f%%입니다.";

    private LottoMachineDisplay() {
    }

    public static void printPromptPurchaseLottoAmount() {
        System.out.println(PROMPT_PURCHASE_LOTTO_AMOUNT_MESSAGE);
    }

    public static void printPromptWinningLottoNumber() {
        System.out.println(PROMPT_WINNING_LOTTO_NUMBER_MESSAGE);
    }

    public static void printPromptBonusLottoNumber() {
        System.out.println(PROMPT_BONUS_LOTTO_NUMBER_MESSAGE);
    }

    public static void printPurchaseLottoCount(int purchaseLottoCount) {
        System.out.println(purchaseLottoCount + PURCHASED_LOTTO_MESSAGE);
    }

    public static void printPurchaseLottoNumbers(int purchaseLottoCount, List<Lotto> lottos) {
        System.out.println(getPurchaseLottos(lottos));
    }

    private static String getPurchaseLottos(List<Lotto> lottos) {
        return lottos.stream()
                .map(lotto -> START_BRACKET + lotto.toString() + END_BRACKET)
                .collect(Collectors.joining(NEW_LINE));
    }

    public static void printLottoStatistics() {
        System.out.println(LOTTO_STATISTICS_MESSAGE + NEW_LINE+ HYPHEN.repeat(3));
    }

    public static void printLottoStatisticsResults(Map<LottoRank, Integer> lottoRanks) {
        StringBuilder message = new StringBuilder();
        for (LottoRank lottoRank : LottoRank.values()) {
            if (!lottoRank.equals(LottoRank.NONE_PRIZE) && lottoRanks.containsKey(lottoRank)) {
                message.append(getLottoStatisticsMessage(lottoRank, lottoRanks.get(lottoRank))).append(NEW_LINE);
            }
        }

        System.out.println(message);
    }

    private static String getLottoStatisticsMessage(LottoRank lottoRank, int prizeWinningCount) {
        int matchLottoNumber = lottoRank.getMatchLottoNumber();
        String prizeMoney = StringUtils.convertToMoneyFormat(lottoRank.getPrizeMoney());

        String headMessage = getHeadMessage(matchLottoNumber, prizeMoney);
        String tailMessage = getTailMessage(prizeWinningCount);

        if(lottoRank.equals(LottoRank.SECOND_PRIZE)) {
            return headMessage
                    + COMMA
                    + BLANK
                    + MATCHED_BONUS_LOTTO_MESSAGE
                    + tailMessage;
        }

        return headMessage + tailMessage;
    }

    private static String getTailMessage(int prizeWinningCount) {
        return BLANK
                + HYPHEN
                + BLANK
                + prizeWinningCount + LOTTO_COUNT_UNIT_MESSAGE;
    }

    private static String getHeadMessage(int matchLottoNumber, String prizeMoney) {
        return matchLottoNumber
                + MATCHED_LOTTO_MESSAGE
                + BLANK
                + START_SMALL_BRACKET + prizeMoney + END_SMALL_BRACKET;
    }

    public void printTotalLottoYieldRateMessage(double yieldRate) {
        String message = String.format(TOTAL_LOTTO_YIELD_RATE, yieldRate);
        System.out.print(message);
    }
}
