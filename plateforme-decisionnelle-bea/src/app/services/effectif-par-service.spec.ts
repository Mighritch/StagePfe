import { TestBed } from '@angular/core/testing';

import { EffectifParService } from './effectif-par-service';

describe('EffectifParService', () => {
  let service: EffectifParService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EffectifParService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
