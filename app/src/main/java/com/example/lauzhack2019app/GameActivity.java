package com.example.lauzhack2019app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    public enum PLAYER_TYPE { SINGLE_PLAYER, MULTIPLAYER }

    public static final String PLAYER_TYPE_TAG = "PlayerType";
    //public static final int TIMER_MAX = 30;
    public static final int TIMER_MAX = 10;
    public static final int TURN_MAX = 10;

    private PLAYER_TYPE playerType;
    private TextView timerText;
    private TextView turnText;
    private TextView cpuMoneyText;

    private int appleAmount = 0;
    private int googleAmount = 0;
    private int microsoftAmount = 0;
    private int spotifyAmount = 0;
    private int amazonAmount = 0;

    private Challenge challenge;

    private BigDecimal yourMoney;

    private int timerTemp = TIMER_MAX;
    private int turnTemp = 1;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        playerType = (PLAYER_TYPE) intent.getSerializableExtra(PLAYER_TYPE_TAG);
        timerText = findViewById(R.id.title_timer);
        turnText = findViewById(R.id.title_turn);
        cpuMoneyText = findViewById(R.id.title_oponent_amount);

        setBuySellButtons();
        setPrices();

        TextView opponentName = findViewById(R.id.title_oponent);
        if(playerType == PLAYER_TYPE.SINGLE_PLAYER) {
            opponentName.setText(R.string.cpu);
        } else if(playerType == PLAYER_TYPE.MULTIPLAYER) {
            opponentName.setText(R.string.rival);
        } else {
            throw new RuntimeException("Not possible");
        }
        timer.schedule(new UpdateTimerText(), 0, 1000);
    }

    private void buyStock(final View v, final int number) {
        BigDecimal price = getPriceBasedOnIndex(number);
        if(price.compareTo(yourMoney) < 1) {
            yourMoney = yourMoney.subtract(price);
            final TextView textView = getQuantityTextViewBasedOnIndex(number);
            final TextView money = findViewById(R.id.title_player_amount);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(incrementQuantityBasedOnIndex(number));
                    money.setText(bigDecimalToString(yourMoney) + "$");
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(v.getContext(), "You don't have enough money",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void sellStock(final View v, final int number) {
        int amount = getQuantityStockBasedOnIndex(number);
        if(amount > 0) {
            BigDecimal price = getPriceBasedOnIndex(number);
            yourMoney = yourMoney.add(price);
            final TextView textView = getQuantityTextViewBasedOnIndex(number);
            final TextView money = findViewById(R.id.title_player_amount);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(decrementQuantityBasedOnIndex(number));
                    money.setText(bigDecimalToString(yourMoney) + "$");
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(v.getContext(), "You don't have enough stock",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private int getQuantityStockBasedOnIndex(int number) {
        if (number == 1) {
            return appleAmount;
        } else if (number == 2) {
            return googleAmount;
        } else if (number == 3) {
            return microsoftAmount;
        } else if (number == 4) {
            return spotifyAmount;
        } else {
            return amazonAmount;
        }
    }

    private String incrementQuantityBasedOnIndex(int number) {
        if (number == 1) {
            return String.valueOf(++appleAmount);
        } else if (number == 2) {
            return String.valueOf(++googleAmount);
        } else if (number == 3) {
            return String.valueOf(++microsoftAmount);
        } else if (number == 4) {
            return String.valueOf(++spotifyAmount);
        } else {
            return String.valueOf(++amazonAmount);
        }
    }

    private String decrementQuantityBasedOnIndex(int number) {
        if (number == 1) {
            return String.valueOf(--appleAmount);
        } else if (number == 2) {
            return String.valueOf(--googleAmount);
        } else if (number == 3) {
            return String.valueOf(--microsoftAmount);
        } else if (number == 4) {
            return String.valueOf(--spotifyAmount);
        } else {
            return String.valueOf(--amazonAmount);
        }
    }

    private TextView getQuantityTextViewBasedOnIndex(int number) {
        if (number == 1) {
            return findViewById(R.id.quantity_apple);
        } else if (number == 2) {
            return findViewById(R.id.quantity_google);
        } else if (number == 3) {
            return findViewById(R.id.quantity_microsoft);
        } else if (number == 4) {
            return findViewById(R.id.quantity_spotify);
        } else {
            return findViewById(R.id.quantity_amazon);
        }
    }

    private TextView getPriceTextViewBasedOnIndex(int number) {
        if (number == 1) {
            return findViewById(R.id.price_apple);
        } else if (number == 2) {
            return findViewById(R.id.price_google);
        } else if (number == 3) {
            return findViewById(R.id.price_microsoft);
        } else if (number == 4) {
            return findViewById(R.id.price_spotify);
        } else {
            return findViewById(R.id.price_amazon);
        }
    }

    private ImageView getIncImageViewBasedOnIndex(int number) {
        if (number == 1) {
            return findViewById(R.id.apple_price_tendency);
        } else if (number == 2) {
            return findViewById(R.id.google_price_tendency);
        } else if (number == 3) {
            return findViewById(R.id.microsoft_price_tendency);
        } else if (number == 4) {
            return findViewById(R.id.spotify_price_tendency);
        } else {
            return findViewById(R.id.amazon_price_tendency);
        }
    }

    private BigDecimal getPriceBasedOnIndex(int number) {
        if (number == 1) {
            return challenge.getApplePrice();
        } else if (number == 2) {
            return challenge.getGooglePrice();
        } else if (number == 3) {
            return challenge.getMicrosoftPrice();
        } else if (number == 4) {
            return challenge.getSpotifyPrice();
        } else {
            return challenge.getAmazonPrice();
        }
    }

    private void setBuySellButtons() {
        Button buyApple = findViewById(R.id.buy_apple);
        buyApple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyStock(v, 1);
            }
        });
        Button sellApple = findViewById(R.id.sell_apple);
        sellApple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellStock(v, 1);
            }
        });

        Button buyGoogle = findViewById(R.id.buy_google);
        buyGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyStock(v, 2);
            }
        });
        Button sellGoogle = findViewById(R.id.sell_google);
        sellGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellStock(v, 2);
            }
        });

        Button buyMicrosoft = findViewById(R.id.buy_microsoft);
        buyMicrosoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyStock(v, 3);
            }
        });
        Button sellMicrosoft = findViewById(R.id.sell_microsoft);
        sellMicrosoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellStock(v, 3);
            }
        });

        Button buySpotify = findViewById(R.id.buy_spotify);
        buySpotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyStock(v, 4);
            }
        });
        Button sellSpotify = findViewById(R.id.sell_spotify);
        sellSpotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellStock(v, 4);
            }
        });

        Button buyAmazon = findViewById(R.id.buy_amazon);
        buyAmazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyStock(v, 5);
            }
        });
        Button sellAmazon = findViewById(R.id.sell_amazon);
        sellAmazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellStock(v, 5);
            }
        });
    }

    private String bigDecimalToString(BigDecimal bigDecimal) {
        bigDecimal = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);
        return df.format(bigDecimal);
    }

    private void setPrices() {
        yourMoney = BigDecimal.valueOf(Double.parseDouble(getString(R.string.start_money).replace("$", "")));
        BigDecimal applePrice = BigDecimal.valueOf(Double.parseDouble(getString(R.string.stock_price_1).replace("$", "")));
        BigDecimal googlePrice = BigDecimal.valueOf(Double.parseDouble(getString(R.string.stock_price_2).replace("$", "")));
        BigDecimal microsoftPrice = BigDecimal.valueOf(Double.parseDouble(getString(R.string.stock_price_3).replace("$", "")));
        BigDecimal spotifyPrice = BigDecimal.valueOf(Double.parseDouble(getString(R.string.stock_price_4).replace("$", "")));
        BigDecimal amazonPrice = BigDecimal.valueOf(Double.parseDouble(getString(R.string.stock_price_5).replace("$", "")));
        BigDecimal cpuMoney = BigDecimal.valueOf(Double.parseDouble(getString(R.string.start_money).replace("$", "")));
        challenge = new Challenge(applePrice, googlePrice, microsoftPrice, spotifyPrice, amazonPrice, cpuMoney);
    }

    private void updatePrices(final List<Challenge.ComputationResult> res) {
        for(int i = 0; i < res.size(); i++) {
            final TextView textView = getPriceTextViewBasedOnIndex(i + 1);
            final ImageView imageView = getIncImageViewBasedOnIndex(i + 1);
            final int finalI = i;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Challenge.ComputationResult result = res.get(finalI);
                    textView.setText(bigDecimalToString(result.getPrice()));
                    if(result.getIncreased() == 1) {
                        imageView.setBackgroundResource(R.drawable.up);
                    } else if (result.getIncreased() == -1) {
                        imageView.setBackgroundResource(R.drawable.down);
                    } else {
                        imageView.setBackgroundResource(R.drawable.equal);
                    }
                }
            });
        }
    }

    class UpdateTimerText extends TimerTask {
        public void run() {
            if(timerTemp > 0) {
                timerTemp--;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerText.setText(String.valueOf(timerTemp));
                    }
                });
            } else {
                timer.cancel();
                timerTemp = TIMER_MAX;
                if(turnTemp < TURN_MAX) {
                    challenge.cpuBuyOrSale();
                    turnTemp++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            turnText.setText("Turn " + String.valueOf(turnTemp));
                            timerText.setText(String.valueOf(timerTemp));
                            cpuMoneyText.setText(bigDecimalToString(challenge.getCpuMoney()) + "$");
                        }
                    });
                    if(playerType == PLAYER_TYPE.SINGLE_PLAYER) {
                        // TODO: Generate random data
                        List<Challenge.ComputationResult> results = challenge.generateRandomPrices();
                        updatePrices(results);
                    } else if(playerType == PLAYER_TYPE.MULTIPLAYER) {
                        // Request Data from server
                        throw new RuntimeException("Not implemented");
                    } else {
                        throw new RuntimeException("Not possible");
                    }
                    // Request Data from server or local
                    timer = new Timer();
                    timer.schedule(new UpdateTimerText(), 0, 1000);
                } else {
                    challenge.cpuSellAll();
                    BigDecimal cpuMoney = challenge.getCpuMoney();
                    String message = "You " + bigDecimalToString(yourMoney) + "$, CPU " +
                            bigDecimalToString(cpuMoney);
                    if (yourMoney.compareTo(cpuMoney) == 1) {
                        message += ". You won.";
                    } else if (yourMoney.compareTo(cpuMoney) == -1) {
                        message += ". You lost.";
                    } else {
                        message += ". You draw.";
                    }
                    final String finalMessage = message;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    getApplicationContext(), finalMessage, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                }
            }
        }
    }
}
