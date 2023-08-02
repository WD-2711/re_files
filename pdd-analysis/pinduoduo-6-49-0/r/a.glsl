#ifdef GL_ES
#ifdef GL_FRAGMENT_PRECISION_HIGH
precision highp float;
#else
precision mediump float;
#endif
#endif

varying vec2 textureCoordinate;
uniform sampler2D inputImageTexture;
uniform sampler2D inputImageTexture2;
uniform vec2 u_resolution;
uniform float u_time;

uniform int contentMode;///< 0:scaleToFill 1:aspectFit 2:aspectFill
uniform float leftSize[2];
uniform float rightSize[2];
uniform int coproductionType;///Horizontal = 0 VerticalTwo = 1 VerticalThree = 2 PictureInPicture = 3

vec4 adjustColor(vec2 imageSize,sampler2D imageTexture, vec2 uv, vec2 containerSize, vec4 bgColor, int contentMode){
    ///< 加黑边
    vec4 resultColor = bgColor;
    if(contentMode == 1 || contentMode == 2){

        bool outputHeightIsBigger = containerSize.y >= containerSize.x;///< 输入的高是否更大
        float inputAspectRatio = imageSize.x / imageSize.y;///< 输入size比例
        float outputAspectRatio = containerSize.x / containerSize.y;///< 输出size比例
        bool isAutomaticHeight = false;///<自适应高

        if(outputHeightIsBigger){
            isAutomaticHeight = inputAspectRatio <= outputAspectRatio ? false : true;
        }else{
            isAutomaticHeight = inputAspectRatio >= outputAspectRatio ? true : false;
        }

        if(contentMode == 2) {
            isAutomaticHeight = !isAutomaticHeight;
        }

        if(isAutomaticHeight){
            float height = imageSize.y / (imageSize.x / containerSize.x);
            float ndcHeight = height / containerSize.y;
            float ndcTop = 0.5 - ndcHeight/2.0;
            float ndcBottom = 0.5 + ndcHeight/2.0;

            if(uv.y >= ndcTop && uv.y <= ndcBottom){
                resultColor = texture2D(imageTexture,vec2(uv.x,(uv.y - ndcTop)*(1.0/ndcHeight)));
            }
        }else{
            float width = imageSize.x / (imageSize.y / containerSize.y);
            float ndcWidth = width / containerSize.x;
            float ndcLeft = 0.5 - ndcWidth/2.0;
            float ndcRight = 0.5 + ndcWidth/2.0;

            if(uv.x >= ndcLeft && uv.x <= ndcRight){
                resultColor = texture2D(imageTexture,vec2((uv.x - ndcLeft)*(1.0/ndcWidth),uv.y));
            }
        }

    }else{
        resultColor = texture2D(imageTexture,uv);
    }
    return resultColor;
}

void main(){
    vec2 leftInputSize;
    leftInputSize.x = leftSize[0];
    leftInputSize.y = leftSize[1];

    vec2 rightInputSize;
    rightInputSize.x = rightSize[0];
    rightInputSize.y = rightSize[1];

    vec2 uv = textureCoordinate;
    vec4 bgColor = vec4(0.0,0.0,0.0,1.0);
    vec4 resultColor = bgColor;

    if(coproductionType == 0){
        if(uv.x <= 0.5){
            uv = vec2(uv.x*2.0,uv.y);
            resultColor = adjustColor(leftInputSize,inputImageTexture,uv,vec2(u_resolution.x/2.0,u_resolution.y),bgColor,contentMode);
        }else{
            uv = vec2((uv.x - 0.5)*2.0,uv.y);
            resultColor = adjustColor(rightInputSize,inputImageTexture2,uv,vec2(u_resolution.x/2.0,u_resolution.y),bgColor,contentMode);
        }
    }else if(coproductionType == 1){
        if(uv.y <= 0.5){
            uv = vec2(uv.x,uv.y*2.0);
            float height = leftInputSize.x * rightInputSize.y / rightInputSize.x;
            if(height < leftInputSize.y / 2.0){
                float ndcHeight = height / (leftInputSize.y / 2.0);
                float ndcTop = 0.5 - ndcHeight/2.0;
                float ndcBottom = 0.5 + ndcHeight/2.0;

                if(uv.y >= ndcTop && uv.y <= ndcBottom){
                    resultColor = texture2D(inputImageTexture2,vec2(uv.x,(uv.y - ndcTop)*(1.0/ndcHeight)));
                }else{
                    resultColor = bgColor;
                }
            }else{
                resultColor = texture2D(inputImageTexture2,vec2(uv.x,uv.y*0.5));
            }
        }else{
            uv = vec2(uv.x,(uv.y-0.5)*2.0);
            float height = rightInputSize.x * leftInputSize.y / leftInputSize.x;
            if(height < rightInputSize.y / 2.0){
                float ndcHeight = height / (rightInputSize.y / 2.0);
                float ndcTop = 0.5 - ndcHeight/2.0;
                float ndcBottom = 0.5 + ndcHeight/2.0;
                if(uv.y >= ndcTop && uv.y <= ndcBottom){
                    resultColor = texture2D(inputImageTexture,vec2(uv.x,(uv.y - ndcTop)*(1.0/ndcHeight)));
                }else{
                    resultColor = bgColor;
                }
            }else{
               resultColor =  texture2D(inputImageTexture, vec2(uv.x,uv.y*0.5));
            }

        }
    }else if(coproductionType == 2){
        if(uv.y >= 0.33 && uv.y <= 0.66){
            resultColor = texture2D(inputImageTexture,uv);
        }else{
            if(uv.y >= 0.66){
                uv = vec2(uv.x,uv.y - 0.66);
            }

            uv = vec2(uv.x,uv.y*3.0);
            float height = leftInputSize.x * rightInputSize.y / rightInputSize.x;
            if(height < leftInputSize.y / 3.0){
                float ndcHeight = height / (leftInputSize.y / 3.0);
                float ndcTop = 0.5 - ndcHeight/2.0;
                float ndcBottom = 0.5 + ndcHeight/2.0;

                if(uv.y >= ndcTop && uv.y <= ndcBottom){
                    resultColor = texture2D(inputImageTexture2,vec2(uv.x,(uv.y - ndcTop)*(1.0/ndcHeight)));
                }else{
                    resultColor = bgColor;
                }
            }else{
                float ndcHeight = leftInputSize.y / 3.0 / height;
                resultColor = texture2D(inputImageTexture2,vec2(uv.x,uv.y*ndcHeight));
            }
        }
    }else if(coproductionType == 3){
        if(rightInputSize.y >= rightInputSize.x){
            float percentX = 0.33;
            float newSizeX = percentX * leftInputSize.x;
            float newSizeY = newSizeX * rightInputSize.y / rightInputSize.x;
            float percentY = newSizeY / leftInputSize.y;
            if(uv.x >= 0.0 && uv.x <= percentX && uv.y >= 0.0 && uv.y <= percentY){
                uv = vec2(uv.x* (1.0/percentX),uv.y*(1.0/percentY));
                resultColor = texture2D(inputImageTexture2,uv);
            }else{
                resultColor = texture2D(inputImageTexture,uv);
            }
        }else{
            float percentX = 0.5;
            float newSizeX = percentX * leftInputSize.x;
            float newSizeY = newSizeX * rightInputSize.y / rightInputSize.x;
            float percentY = newSizeY / leftInputSize.y;
            if(uv.x >= 0.0 && uv.x <= percentX && uv.y >= 0.0 && uv.y <= percentY){
                uv = vec2(uv.x* (1.0/percentX),uv.y*(1.0/percentY));
                resultColor = texture2D(inputImageTexture2,uv);
            }else{
                resultColor = texture2D(inputImageTexture,uv);
            }
        }
    }

    gl_FragColor = resultColor;
}