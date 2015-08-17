#pragma once

class ImageUtil
{
public:
	ImageUtil(void);

	int getRed(BYTE * ptr, int x, int y);
	int getGreen(BYTE * ptr, int x, int y);
	int getBlue(BYTE * ptr, int x, int y);

	int getAvgColor(BYTE * ptr, int x, int y);

	void convertBlackWhite(BYTE * ptr, int y, int threshold, BYTE * result);
	void convertBlackWhite(BYTE * ptr, int * yVals, int threshold, BYTE * result);

	void convertBlackWhite(BYTE * ptr, int y, int threshold, int * background, BYTE * result);
	void convertBlackWhite(BYTE * ptr, int * yVals, int threshold, int * background, BYTE * result);

	int convertBlackWhite(BYTE * ptr, int x, int y, int threshold);

	void setPixel(BYTE * ptr, int x, int y, int r, int g, int b);

	void drawLine(BYTE * ptr, int x0, int y0, int x1, int y1, int r, int g, int b);
	void drawVertLine(BYTE * ptr, int x0, int r, int g, int b);
	void drawHorzLine(BYTE * ptr, int y0, int r, int g, int b);

	bool isAll(BYTE * ptr, int val);

	~ImageUtil(void);

private:
	int toIndex(int x, int y);
	int round(float val);
};
