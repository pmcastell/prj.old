__global__ void _convolution2DImage(float *N, const float *M, float *P, int Width, int Height, int Mask_Width) {
	__shared__ float N_ds[TILE_SIZE+Mask_width-1][TILE_SIZE+Mask_width-1][CHANNELS];
	int tx=threadIdx.x, ty=threadIdx.y, bx=blockIdx.x, by=blockIdx.y, totalWidth=CHANNELS*Width, totalTileSize=TILE_SIZE*CHANNELS;
	//int j=tx*blockIdx.x*CHANNELS+
	int indx=(bx*blockDim.x+tx)*CHANNELS;
	int indy=by*blockDim.y+ty;
	if (indx<totalWidth && indy<Height) {
		int halo_indexX,
			//n=CHANNELS*Mask_radius,
			halo_indexY;
		if (tx>=TILE_SIZE-Mask_radius) { // left
			halo_indexX=indx-totalTileSize;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty][tx-(TILE_SIZE-Mask_radius)][k]=(halo_indexX<0 ? 0 :N[indy*totalWidth+halo_indexX+k]);
			}
		}
		if (ty>=TILE_SIZE-Mask_radius) { // up
			halo_indexY=indy-TILE_SIZE*totalWidth;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty-(TILE_SIZE-Mask_radius)][tx][k]=(halo_indexY<0 ? 0 :N[halo_indexY*totalWidth+indx+k]);
			}
		}
		if (tx<Mask_radius) { // right
			halo_indexX=indx+totalTileSize;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty][tx+TILE_SIZE][k]=(halo_indexX>=totalWidth ? 0:N[indy*totalWidth+halo_indexX+k]);
			}
		}
		if (ty<Mask_radius) {  //bottom
			halo_indexY=indy+TILE_SIZE*totalWidth;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty+(TILE_SIZE+Mask_radius)][tx][k]=(halo_indexY>=TILE_SIZE?0:N[halo_indexY*totalWidth+indx+k]);
			}
		}
		if (tx<Mask_radius && ty<Mask_radius) { //left upper corner
			int ind=(indy-Mask_radius)*totalWidth+indx-Mask_radius*CHANNELS;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty][tx][k]=(ind<0 ? 0: N[ind+k]);
			}
		}
		if (tx<Mask_radius && ty>=TILE_SIZE-Mask_radius) { //left bottom corner
			int ind=(indy+Mask_radius)*totalWidth+indx-Mask_radius*CHANNELS;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty+Mask_radius][tx][k]=(ind>=totalWidth? 0: N[ind+k]);
			}
		}
		if (tx>=TILE_SIZE-Mask_radius && ty<Mask_radius) { // right upper corner
			int ind=(indy-Mask_radius)*totalWidth+indx+Mask_radius*CHANNELS;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty][tx+Mask_radius][k]=(ind>=totalWidth? 0: N[ind+k]);
			}
		}
		if (tx>=TILE_SIZE-Mask_radius && ty>=TILE_SIZE-Mask_radius) { //right bottom corner
			int ind=(indy+Mask_radius)*totalWidth+indx+Mask_radius*CHANNELS;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty+Mask_radius][tx+Mask_radius][k]=(ind>=totalWidth?0:N[ind+k]);
			}
		}
		for(int k=0;k<CHANNELS;k++) {
			N_ds[ty+Mask_radius][tx+Mask_radius][k]=N[indy*totalWidth+indx+k];
		}
		__syncthreads();
		float PValue[CHANNELS]={0,0,0};
		for(int k=0;k<CHANNELS;k++) {
			for(int i=0;i<Mask_width;i++) {
				for(int j=0;j<Mask_width;j++) {
					PValue[k]+=N_ds[ty+i][tx+j][k]*M[i*Mask_width+j];
				}
			}
			P[indy*totalWidth+indx+k]=clamp(PValue[k],0,1);
		}

	}
}


__global__ void convolution2DImage(float *N, const float *M, float *P, int Width, int Height, int Mask_Width) {
	__shared__ float N_ds[TILE_SIZE+Mask_width-1][TILE_SIZE+Mask_width-1][CHANNELS];
	int tx=threadIdx.x, ty=threadIdx.y, bx=blockIdx.x, by=blockIdx.y, totalWidth=CHANNELS*Width, totalTileSize=TILE_SIZE*CHANNELS;
	//int j=tx*blockIdx.x*CHANNELS+
	int indx=(bx*blockDim.x+tx)*CHANNELS;
	int indy=by*blockDim.y+ty;
	if (indx<totalWidth && indy<Height) {
		int halo_indexX,
			//n=CHANNELS*Mask_radius,
			halo_indexY;
		if (tx>=TILE_SIZE-Mask_radius) { // left
			halo_indexX=indx-totalTileSize;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty][tx-(TILE_SIZE-Mask_radius)][k]=(halo_indexX<0 ? 0 :N[indy*totalWidth+halo_indexX+k]);
			}
		}
		if (ty>=TILE_SIZE-Mask_radius) { // up
			halo_indexY=indy-TILE_SIZE*totalWidth;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty-(TILE_SIZE-Mask_radius)][tx][k]=(halo_indexY<0 ? 0 :N[halo_indexY*totalWidth+indx+k]);
			}
		}
		if (tx<Mask_radius) { // right
			halo_indexX=indx+totalTileSize;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty][tx+TILE_SIZE+Mask_radius][k]=(halo_indexX>=totalWidth ? 0:N[indy*totalWidth+halo_indexX+k]);
			}
		}
		if (ty<Mask_radius) {  //bottom
			halo_indexY=indy+TILE_SIZE*totalWidth;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty+(TILE_SIZE+Mask_radius)][tx][k]=(halo_indexY>=TILE_SIZE?0:N[halo_indexY*totalWidth+indx+k]);
			}
		}
		if (tx<Mask_radius && ty<Mask_radius) { //left upper corner
			int ind=(indy-Mask_radius)*totalWidth+indx-Mask_radius*CHANNELS;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty][tx][k]=(ind<0 ? 0: N[ind+k]);
			}
		}
		if (tx<Mask_radius && ty>=TILE_SIZE-Mask_radius) { //left bottom corner
			int ind=(indy+Mask_radius)*totalWidth+indx-Mask_radius*CHANNELS;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty+Mask_radius][tx][k]=(ind>=totalWidth? 0: N[ind+k]);
			}
		}
		if (tx>=TILE_SIZE-Mask_radius && ty<Mask_radius) { // right upper corner
			int ind=(indy-Mask_radius)*totalWidth+indx+Mask_radius*CHANNELS;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty][tx+Mask_radius][k]=(ind>=totalWidth? 0: N[ind+k]);
			}
		}
		if (tx>=TILE_SIZE-Mask_radius && ty>=TILE_SIZE-Mask_radius) { //right bottom corner
			int ind=(indy+Mask_radius)*totalWidth+indx+Mask_radius*CHANNELS;
			for(int k=0;k<CHANNELS;k++) {
				N_ds[ty+Mask_radius][tx+Mask_radius][k]=(ind>=totalWidth?0:N[ind+k]);
			}
		}
		for(int k=0;k<CHANNELS;k++) {
			N_ds[ty+Mask_radius][tx+Mask_radius][k]=N[indy*totalWidth+indx+k];
		}
		__syncthreads();
		float PValue[CHANNELS]={0,0,0};
		for(int k=0;k<CHANNELS;k++) {
			for(int i=0;i<Mask_width;i++) {
				for(int j=0;j<Mask_width;j++) {
					PValue[k]+=N_ds[ty+i][tx+j][k]*M[i*Mask_width+j];
				}
			}
			P[indy*totalWidth+indx+k]=clamp(PValue[k],0,1);
		}

	}
}
__global__ void __convolution2DImage(float *N, const float *M, float *P, int Width, int Height, int Mask_Width) {
	int indy=blockIdx.y*blockDim.y+threadIdx.y;
	int indx=blockIdx.x*blockDim.x*CHANNELS+threadIdx.x;
	int totalWidth=Width*CHANNELS;
	//for(int k=0;k<CHANNELS;k++) {
		P[indy*totalWidth+indx+0]=0.458824;
		P[indy*totalWidth+indx+1]=0.290196;
		P[indy*totalWidth+indx+2]=0.137255;
	//}
}
