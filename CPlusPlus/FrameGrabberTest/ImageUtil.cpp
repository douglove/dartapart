#include "stdafx.h"
#include "math.h"
#include ".\imageutil.h"

ImageUtil::ImageUtil(void)
{
}

ImageUtil::~ImageUtil(void)
{
}


/**
 *
 **/
int ImageUtil::getRed(BYTE * ptr, int x, int y)
{
	return ptr[toIndex(x, y)];
}

int ImageUtil::getGreen(BYTE * ptr, int x, int y)
{
	return ptr[toIndex(x, y) + 1];
}

int ImageUtil::getBlue(BYTE * ptr, int x, int y)
{
	return ptr[toIndex(x, y) + 2];
}

int ImageUtil::getAvgColor(BYTE * ptr, int x, int y)
{
	int index = toIndex(x, y);
	return (ptr[index] + ptr[index+1] + ptr[index+2]) / 3;
}

int ImageUtil::convertBlackWhite(BYTE * ptr, int x, int y, int threshold)
{
	int color = 0;
	if (getAvgColor(ptr, x, y) < threshold)
	{   // black
		color = 0;
		setPixel(ptr, x, y, 0, 0, 0);
	}
	else
	{   // white
		color = 1;
		setPixel(ptr, x, y, 255, 255, 255);
	}
	return color;
}

void ImageUtil::convertBlackWhite(BYTE * ptr, int y, int threshold, BYTE * result)
{
	for (int x = 0; x < 640; x++) 
	{
		if (getAvgColor(ptr, x, y) < threshold)
		{   // black
			result[x] = 0;
			setPixel(ptr, x, y, 0, 0, 0);
		}
		else
		{   // white
			result[x] = 1;
			setPixel(ptr, x, y, 255, 255, 255);
		}
	}
}

void ImageUtil::convertBlackWhite(BYTE * ptr, int * yVals, int threshold, BYTE * result)
{
	for (int x = 0; x < 640; x++) 
	{
		int y = yVals[x];
		if (getAvgColor(ptr, x, y) < threshold)
		{   // black
			result[x] = 0;
			setPixel(ptr, x, y, 0, 0, 0);
		}
		else
		{   // white
			result[x] = 1;
			setPixel(ptr, x, y, 255, 255, 255);
		}
	}
}

void ImageUtil::convertBlackWhite(BYTE * ptr, int y, int threshold, int * background, BYTE * result)
{
	for (int x = 0; x < 640; x++) 
	{
		int avgColor = getAvgColor(ptr, x, y);
		if (avgColor - background[x] > threshold)
		{   // black
			result[x] = 0;
			setPixel(ptr, x, y, 0, 0, 0);
		}
		else
		{   // white
			result[x] = 1;
			setPixel(ptr, x, y, 255, 255, 255);
		}
	}
}

void ImageUtil::convertBlackWhite(BYTE * ptr, int * yVals, int threshold, int * background, BYTE * result)
{
	for (int x = 0; x < 640; x++) 
	{
		int y = yVals[x];
		if (abs(getAvgColor(ptr, x, y) - background[x]) > threshold)
		{   // black
			result[x] = 0;
			setPixel(ptr, x, y, 0, 0, 0);
		}
		else
		{   // white
			result[x] = 1;
			setPixel(ptr, x, y, 255, 255, 255);
		}
	}
}
/**
 *
 **/
void ImageUtil::setPixel(BYTE * ptr, int x, int y, int r, int g, int b)
{
	int index = toIndex(x, y);
	ptr[index] = b;
	ptr[index+1] = g;
	ptr[index+2] = r;
}

void ImageUtil::drawLine(BYTE * ptr, int x0, int y0, int x1, int y1, int r, int g, int b)
{
    int dx = x1 - x0;
    int dy = y1 - y0;

    setPixel(ptr, x0, y0, r, g, b);
    if (abs(dx) > abs(dy)) {          // slope < 1
        float m = (float) dy / (float) dx;      // compute slope
        float b = y0 - m*x0;
        dx = (dx < 0) ? -1 : 1;
        while (x0 != x1) {
            x0 += dx;
            setPixel(ptr, x0, (int) round(m*x0 + b), r, g, b);
        }
    } else
    if (dy != 0) {                              // slope >= 1
        float m = (float) dx / (float) dy;      // compute slope
        float b = x0 - m*y0;
        dy = (dy < 0) ? -1 : 1;
        while (y0 != y1) {
            y0 += dy;
            setPixel(ptr, (int) round(m*y0 + b), y0, r, g, b);
        }
    }
}

void ImageUtil::drawVertLine(BYTE * ptr, int x0, int r, int g, int b)
{
	drawLine(ptr, x0, 0, x0, 479, r, g, b);
}

void ImageUtil::drawHorzLine(BYTE * ptr, int y0, int r, int g, int b)
{
	drawLine(ptr, 0, y0, 639, y0, r, g, b);
}

bool ImageUtil::isAll(BYTE * ptr, int val) 
{
	for (int i = 0; i < 640; i++) {
		if (ptr[i] != val) {
			return false;
		}
	}
	return true;
}

/**
 *
 **/
int ImageUtil::toIndex(int x, int y)
{
	return (x + (y * 640)) * 3;
}

int ImageUtil::round(float val) 
{
	return (int) (val + .5);
}

